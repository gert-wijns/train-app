package be.gert.trainapp.sm.network.node;


import be.gert.trainapp.api.network.generated.RenameNodeUseCaseApi;
import be.gert.trainapp.api.network.generated.model.RenameNodeRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class RenameNodeUseCase implements RenameNodeUseCaseApi {
	private final NodeJpaRepository jpa;

	@Override
	@Transactional
	public void execute(RenameNodeRequest request) {
		var node = jpa.getById(new NodeId(request.getId()));

		jpa.save(node.rename(request.getNewName()));
	}
}
