package be.gert.trainapp.sm.network.node;

import be.gert.trainapp.api.network.generated.DecommissionNodeUseCaseApi;
import be.gert.trainapp.api.network.generated.model.DecommissionNodeRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network._model.Node;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class DecommissionNodeUseCase implements DecommissionNodeUseCaseApi {
	private final NodeJpaRepository jpa;

	@Override
	@Transactional
	public void execute(DecommissionNodeRequest request) {
		NodeId id = new NodeId(request.getId());
		Node node = jpa.getById(id);

		jpa.save(node.decommission());
	}
}
