package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._mapper.GeoPositionMapper.toGeoPosition;

import be.gert.trainapp.api.network.generated.RepositionNodeUseCaseApi;
import be.gert.trainapp.api.network.generated.model.RepositionNodeRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class RepositionNodeUseCase implements RepositionNodeUseCaseApi {
	private final NodeJpaRepository jpa;

	@Override
	@Transactional
	public void execute(RepositionNodeRequest request) {
		var node = jpa.getById(new NodeId(request.getId()));

		jpa.save(node.reposition(toGeoPosition(request.getNewGeoPosition())));
	}
}
