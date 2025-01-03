package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._mapper.GeoPositionMapper.toGeoPosition;
import static be.gert.trainapp.sm.network.node.model.Node.newNode;
import static be.gert.trainapp.sm.network.node.model.NodeExceptions.alreadyExists;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.network.generated.AddNodeUseCaseApi;
import be.gert.trainapp.api.network.generated.model.AddNodeRequest;
import be.gert.trainapp.sm.network.GeoPosition;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network.node.jpa.NodeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddNodeUseCase implements AddNodeUseCaseApi {
	private final NodeJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddNodeRequest request) {
		NodeId id = new NodeId(request.getId());
		if (jpa.findById(id).isPresent()) {
			throw alreadyExists(id);
		}
		GeoPosition geoPosition = toGeoPosition(request.getGeoPosition());
		jpa.save(newNode(id, request.getName(), geoPosition));
		return noContent().build();
	}
}
