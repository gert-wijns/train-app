package be.gert.trainapp.sm.network.node;

import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.network.generated.DecommissionNodeUseCaseApi;
import be.gert.trainapp.api.network.generated.model.DecommissionNodeRequest;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network._model.Node;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class DecommissionNodeUseCase implements DecommissionNodeUseCaseApi {
	private final NodeJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(DecommissionNodeRequest request) {
		NodeId id = new NodeId(request.getId());
		Node node = jpa.getById(id);

		jpa.save(node.decommission());

		return noContent().build();
	}
}
