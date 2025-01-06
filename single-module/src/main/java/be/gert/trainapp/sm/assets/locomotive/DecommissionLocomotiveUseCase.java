package be.gert.trainapp.sm.assets.locomotive;

import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.assets.generated.DecommissionLocomotiveUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.DecommissionLocomotiveRequest;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets._events.LocomotiveDecommissioned;
import be.gert.trainapp.sm.assets._repository.LocomotiveJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class DecommissionLocomotiveUseCase implements DecommissionLocomotiveUseCaseApi {
	private final LocomotiveJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(DecommissionLocomotiveRequest request) {
		var locomotive = jpa.getById(new LocomotiveId(request.getId()));
		jpa.save(locomotive.decommission());
		eventPublisher.publishEvent(new LocomotiveDecommissioned(locomotive.id()));
		return noContent().build();
	}
}
