package be.gert.trainapp.sm.assets.locomotive;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.CONFLICT;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.assets._model.Locomotive.newLocomotive;
import static be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository.notFound;

import org.springframework.context.ApplicationEventPublisher;

import be.gert.trainapp.api.assets.generated.AddLocomotiveUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.AddLocomotiveRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets._events.LocomotiveAddedEvent;
import be.gert.trainapp.sm.assets._repository.LocomotiveJpaRepository;
import be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class AddLocomotiveUseCase implements AddLocomotiveUseCaseApi {
	public static DomainException serialNumberAlreadyExists(SerialNumber serialNumber) {
		return error("ASSETS_LOCOMOTIVE_SERAL_NUMBER_ALREADY_EXISTS",
				"Locomotive with Serial Number '${serialNumber}' already exists .")
				.withParam("serialNumber", serialNumber.sn())
				.asException(CONFLICT);
	}

	private final LocomotiveModelJpaRepository modelJpa;
	private final LocomotiveJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public void execute(AddLocomotiveRequest request) {
		var serialNumber = new SerialNumber(request.getSerialNumber());
		if (jpa.existsBySerialNumberIs(serialNumber)) {
			throw serialNumberAlreadyExists(serialNumber);
		}
		LocomotiveModelId modelId = new LocomotiveModelId(request.getModelTypeId());
		if (!modelJpa.existsById(modelId)) {
			throw notFound(modelId);
		}
		var locomotive = jpa.save(newLocomotive(new LocomotiveId(
				request.getId()),
				request.getName(),
				modelId,
				serialNumber));
		eventPublisher.publishEvent(new LocomotiveAddedEvent(
				locomotive.id(),
				locomotive.modelId(),
				locomotive.serialNumber(),
				locomotive.name()));
	}
}
