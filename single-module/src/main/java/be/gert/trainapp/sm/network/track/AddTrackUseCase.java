package be.gert.trainapp.sm.network.track;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.CONFLICT;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.network._mapper.SpeedMapper.toSpeed;
import static be.gert.trainapp.sm.network._model.Track.newTrack;

import be.gert.trainapp.api.network.generated.AddTrackUseCaseApi;
import be.gert.trainapp.api.network.generated.model.AddTrackRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.network.TrackId;
import be.gert.trainapp.sm.network._model.Track;
import be.gert.trainapp.sm.network._repository.TrackJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class AddTrackUseCase implements AddTrackUseCaseApi {
	public static DomainException alreadyExists(TrackId trackId) {
		return error("NETWORK_TRACK_ALREADY_EXISTS",
				"Track already exists between '${fromId}' and '${toId}'.")
				.withParam("fromId", trackId.from().id())
				.withParam("toId", trackId.to().id())
				.asException(CONFLICT);
	}

	private final TrackJpaRepository jpa;

	@Override
	@Transactional
	public void execute(AddTrackRequest request) {
		TrackId id = new TrackId(
				new NodeId(request.getFromNodeId()),
				new NodeId(request.getToNodeId()));
		if (jpa.findById(id).isPresent()) {
			throw alreadyExists(id);
		}

		Track track = newTrack(id, new TrackGauge(request.getGauge()), request.getSlope());

		if (request.getElectrified()) {
			track.electrify();
		}

		track.adjustSpeedLimit(toSpeed(request.getSpeedLimit()));

		jpa.save(track);
	}
}
