package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.assets._model.Wagon.newWagon;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.assets.generated.AddWagonUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.AddWagonRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets._repository.WagonJpaRepository;
import be.gert.trainapp.sm.assets._events.WagonAddedEvent;
import be.gert.trainapp.sm.network.TrackGauge;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
@Validated
public class AddWagonUseCase implements AddWagonUseCaseApi {
	public static DomainException serialNumberAlreadyExists(SerialNumber serialNumber) {
		return error("ASSETS_WAGON_SERAL_NUMBER_ALREADY_EXISTS",
				"Wagon with Serial Number '${serialNumber}' already exists .")
				.withParam("serialNumber", serialNumber.sn())
				.asException();
	}

	private final WagonJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddWagonRequest request) {
		var serialNumber = new SerialNumber(request.getSerialNumber());
		if (jpa.existsBySerialNumberIs(serialNumber)) {
			throw serialNumberAlreadyExists(serialNumber);
		}
		var wagon = jpa.save(newWagon(
				new WagonId(request.getWagonId()),
				new WagonModelId(request.getModelTypeId()),
				serialNumber,
				new TrackGauge(request.getGauge())));
		eventPublisher.publishEvent(new WagonAddedEvent(
				wagon.id(), wagon.modelId(), wagon.serialNumber()));
		return noContent().build();
	}

}
