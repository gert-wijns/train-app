package be.gert.trainapp.sm.network.node;


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
		var node = jpa.getById(new NodeId(request.getId()));

		jpa.save(node.rename(request.getNewName()));

		return noContent().build();
	}
}
