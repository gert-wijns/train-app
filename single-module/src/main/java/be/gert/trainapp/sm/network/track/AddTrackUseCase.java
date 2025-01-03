package be.gert.trainapp.sm.network.track;

import static be.gert.trainapp.sm.network.track.model.Track.newTrack;
import static be.gert.trainapp.sm.network.track.model.TrackExceptions.alreadyExists;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.network.generated.AddTrackUseCaseApi;
import be.gert.trainapp.api.network.generated.model.AddTrackRequest;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network.Speed;
import be.gert.trainapp.sm.network.Speed.SpeedMeasurement;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.network.TrackId;
import be.gert.trainapp.sm.network.track.jpa.TrackJpaRepository;
import be.gert.trainapp.sm.network.track.model.Track;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddTrackUseCase implements AddTrackUseCaseApi {
	private final TrackJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddTrackRequest request) {
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

		track.adjustSpeedLimit(new Speed(
				request.getSpeedLimit().getSpeed(),
				SpeedMeasurement.valueOf(request.getSpeedLimit().getMeasurement().name())));

		jpa.save(track);

		return noContent().build();
	}
}
