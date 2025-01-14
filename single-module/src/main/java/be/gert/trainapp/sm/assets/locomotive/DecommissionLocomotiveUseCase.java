package be.gert.trainapp.sm.assets.locomotive;

import org.springframework.context.ApplicationEventPublisher;

import be.gert.trainapp.api.assets.generated.DecommissionLocomotiveUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.DecommissionLocomotiveRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets._events.LocomotiveDecommissioned;
import be.gert.trainapp.sm.assets._repository.LocomotiveJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class DecommissionLocomotiveUseCase implements DecommissionLocomotiveUseCaseApi {
	private final LocomotiveJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public void execute(DecommissionLocomotiveRequest request) {
		var locomotive = jpa.getById(new LocomotiveId(request.getId()));
		jpa.save(locomotive.decommission());
		eventPublisher.publishEvent(new LocomotiveDecommissioned(locomotive.id()));
	}
}
