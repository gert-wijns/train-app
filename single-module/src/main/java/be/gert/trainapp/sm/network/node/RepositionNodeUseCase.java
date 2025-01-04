package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._mapper.GeoPositionMapper.toGeoPosition;
import static be.gert.trainapp.sm.network._model.NodeExceptions.notFound;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.network.generated.RepositionNodeUseCaseApi;
import be.gert.trainapp.api.network.generated.model.RepositionNodeRequest;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class RepositionNodeUseCase implements RepositionNodeUseCaseApi {
	private final NodeJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(RepositionNodeRequest request) {
		NodeId id = new NodeId(request.getId());
		var node = jpa.findById(id).orElseThrow(() -> notFound(id));

		jpa.save(node.reposition(toGeoPosition(request.getNewGeoPosition())));

		return noContent().build();
	}
}
