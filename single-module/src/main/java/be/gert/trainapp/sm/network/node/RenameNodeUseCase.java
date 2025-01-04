package be.gert.trainapp.sm.network.node;


import static be.gert.trainapp.sm.network._model.NodeExceptions.notFound;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.network.generated.RenameNodeUseCaseApi;
import be.gert.trainapp.api.network.generated.model.RenameNodeRequest;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class RenameNodeUseCase implements RenameNodeUseCaseApi {
	private final NodeJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(RenameNodeRequest request) {
		NodeId id = new NodeId(request.getId());
		var node = jpa.findById(id).orElseThrow(() -> notFound(id));

		jpa.save(node.rename(request.getNewName()));

		return noContent().build();
	}
}
