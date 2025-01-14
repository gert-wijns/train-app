package be.gert.trainapp.sm.assets.wagon;

import org.springframework.context.ApplicationEventPublisher;

import be.gert.trainapp.api.assets.generated.DecommissionWagonUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.DecommissionWagonRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets._events.WagonDecommissioned;
import be.gert.trainapp.sm.assets._repository.WagonJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class DecommissionWagonUseCase implements DecommissionWagonUseCaseApi {
	private final WagonJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public void execute(DecommissionWagonRequest request) {
		var wagon = jpa.getById(new WagonId(request.getId()));
		jpa.save(wagon.decommission());
		eventPublisher.publishEvent(new WagonDecommissioned(wagon.id()));
	}
}
