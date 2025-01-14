package be.gert.trainapp.sm.network.track;

import be.gert.trainapp.api.network.generated.DecommissionTrackUseCaseApi;
import be.gert.trainapp.api.network.generated.model.DecommissionTrackRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network.TrackId;
import be.gert.trainapp.sm.network._repository.TrackJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class DecommissionTrackUseCase implements DecommissionTrackUseCaseApi {
	private final TrackJpaRepository jpa;

	@Override
	@Transactional
	public void execute(DecommissionTrackRequest request) {
		TrackId id = new TrackId(
				new NodeId(request.getFromNodeId()),
				new NodeId(request.getToNodeId()));

		jpa.save(jpa.getById(id).decomission());
	}
}
