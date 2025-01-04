package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.network._mapper.GeoPositionMapper.toGeoPosition;
import static be.gert.trainapp.sm.network._model.Node.newNode;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.network.generated.AddNodeUseCaseApi;
import be.gert.trainapp.api.network.generated.model.AddNodeRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.values.GeoPosition;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddNodeUseCase implements AddNodeUseCaseApi {
	private final NodeJpaRepository jpa;

	public static DomainException alreadyExists(NodeId nodeId) {
		return error("NETWORK_NODE_ALREADY_EXISTS",
				"Node already exists for id '${id}'.")
				.withParam("id", nodeId.id())
				.asException();
	}

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
