package be.gert.trainapp.sm.assets.wagon;

import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.assets.generated.DecommissionWagonUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.DecommissionWagonRequest;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets._events.WagonDecommissioned;
import be.gert.trainapp.sm.assets._repository.WagonJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class DecommissionWagonUseCase implements DecommissionWagonUseCaseApi {
	private final WagonJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(DecommissionWagonRequest request) {
		var wagon = jpa.getById(new WagonId(request.getId()));
		jpa.save(wagon.decommission());
		eventPublisher.publishEvent(new WagonDecommissioned(wagon.id()));
		return noContent().build();
	}
}
