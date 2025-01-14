package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.CONFLICT;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.assets._model.Wagon.newWagon;
import static be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository.notFound;

import org.springframework.context.ApplicationEventPublisher;

import be.gert.trainapp.api.assets.generated.AddWagonUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.AddWagonRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets._events.WagonAddedEvent;
import be.gert.trainapp.sm.assets._repository.WagonJpaRepository;
import be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class AddWagonUseCase implements AddWagonUseCaseApi {
	public static DomainException serialNumberAlreadyExists(SerialNumber serialNumber) {
		return error("ASSETS_WAGON_SERAL_NUMBER_ALREADY_EXISTS",
				"Wagon with Serial Number '${serialNumber}' already exists .")
				.withParam("serialNumber", serialNumber.sn())
				.asException(CONFLICT);
	}

	private final WagonModelJpaRepository modelJpa;
	private final WagonJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public void execute(AddWagonRequest request) {
		var serialNumber = new SerialNumber(request.getSerialNumber());
		if (jpa.existsBySerialNumberIs(serialNumber)) {
			throw serialNumberAlreadyExists(serialNumber);
		}
		WagonModelId wagonModelId = new WagonModelId(request.getModelTypeId());
		if (!modelJpa.existsById(wagonModelId)) {
			throw notFound(wagonModelId);
		}
		var wagon = jpa.save(newWagon(
				new WagonId(request.getWagonId()),
				wagonModelId,
				serialNumber));
		eventPublisher.publishEvent(new WagonAddedEvent(
				wagon.id(), wagon.modelId(), wagon.serialNumber()));
	}

}
