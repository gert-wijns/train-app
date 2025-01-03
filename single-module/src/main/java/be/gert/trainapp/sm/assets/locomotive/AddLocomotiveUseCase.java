package be.gert.trainapp.sm.assets.locomotive;

import static be.gert.trainapp.sm.assets.locomotive.model.Locomotive.newLocomotive;
import static be.gert.trainapp.sm.assets.locomotive.model.LocomotiveExceptions.serialNumberAlreadyExists;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.assets.generated.AddLocomotiveUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.AddLocomotiveRequest;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.locomotive.jpa.LocomotiveJpaRepository;
import be.gert.trainapp.sm.assets.locomotive.model.events.LocomotiveAddedEvent;
import be.gert.trainapp.sm.network.TrackGauge;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddLocomotiveUseCase implements AddLocomotiveUseCaseApi {
	private final LocomotiveJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddLocomotiveRequest request) {
		var serialNumber = new SerialNumber(request.getSerialNumber());
		if (jpa.existsBySerialNumberIs(serialNumber)) {
			throw serialNumberAlreadyExists(serialNumber);
		}
		var locomotive = jpa.save(newLocomotive(new LocomotiveId(
				request.getId()),
				request.getName(),
				new LocomotiveModelId(request.getModelTypeId()),
				serialNumber,
				new TrackGauge(request.getGauge())));
		eventPublisher.publishEvent(new LocomotiveAddedEvent(
				locomotive.id(),
				locomotive.modelId(),
				locomotive.serialNumber(),
				locomotive.name()));
		return noContent().build();
	}
}
