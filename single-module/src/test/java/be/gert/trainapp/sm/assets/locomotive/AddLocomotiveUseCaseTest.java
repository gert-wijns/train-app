package be.gert.trainapp.sm.assets.locomotive;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.locomotive1937Id;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.locomotiveStainier;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.serialNumberStainier;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5Id;
import static be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository.notFound;
import static be.gert.trainapp.sm.assets.locomotive.AddLocomotiveUseCase.serialNumberAlreadyExists;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import be.gert.trainapp.api.assets.generated.model.AddLocomotiveRequest;
import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.assets._events.LocomotiveAddedEvent;
import be.gert.trainapp.sm.assets._repository.LocomotiveJpaRepository;
import be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository;

@ModuleCoreTest
class AddLocomotiveUseCaseTest {
	@Autowired
	LocomotiveModelJpaRepository modelJpa;
	@Autowired
	LocomotiveJpaRepository jpa;
	@Autowired
	ApplicationEvents events;
	@Autowired
	AddLocomotiveUseCase usecase;

	AddLocomotiveRequest request = new AddLocomotiveRequest()
			.id(locomotive1937Id.id())
			.modelTypeId(locomotiveModelLMSStainierBlack5Id.id())
			.serialNumber(serialNumberStainier.sn())
			.name("Stainier");

	@Test
	void success() {
		// when
		modelJpa.save(locomotiveModelLMSStainierBlack5());
		usecase.execute(request);

		// then
		assertEntity(jpa.getById(locomotive1937Id))
				.isEqualTo(locomotiveStainier());

		assertThat(events.stream(LocomotiveAddedEvent.class))
				.containsExactly(new LocomotiveAddedEvent(
						locomotive1937Id,
						locomotiveModelLMSStainierBlack5Id,
						serialNumberStainier,
						"Stainier"));
	}

	@Test
	void throwModelNotFound() {
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(locomotiveModelLMSStainierBlack5Id));
	}

	@Test
	void throwSerialNumberAlreadyExists() {
		jpa.save(locomotiveStainier());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(serialNumberAlreadyExists(serialNumberStainier));
	}
}
