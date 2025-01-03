package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.sm.assets.wagon.model.Wagon.newWagon;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.assets.generated.AddWagonUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.AddWagonRequest;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets.wagon.jpa.WagonJpaRepository;
import be.gert.trainapp.sm.assets.wagon.model.events.WagonAddedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
@Validated
public class AddWagonUseCase implements AddWagonUseCaseApi {
	private final WagonJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddWagonRequest request) {
		var wagon = jpa.save(newWagon(
				new WagonId(request.getWagonId()),
				new WagonModelId(request.getModelTypeId()),
				new SerialNumber(request.getSerialNumber())));
		eventPublisher.publishEvent(new WagonAddedEvent(
				wagon.id(), wagon.modelId(), wagon.serialNumber()));
		return noContent().build();
	}

}
