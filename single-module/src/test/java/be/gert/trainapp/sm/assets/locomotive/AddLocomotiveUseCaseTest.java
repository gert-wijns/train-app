package be.gert.trainapp.sm.assets.locomotive;

import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.locomotive1937Id;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.locomotiveModelLMSStainierBlack5;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.locomotiveStainier;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.serialNumberStainier;
import static be.gert.trainapp.sm.assets._model.LocomotiveExceptions.serialNumberAlreadyExists;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import be.gert.trainapp.api.assets.generated.model.AddLocomotiveRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;
import be.gert.trainapp.sm.assets._events.LocomotiveAddedEvent;

@ModuleTest
class AddLocomotiveUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	ApplicationEvents events;
	@Autowired
	AddLocomotiveUseCase usecase;

	AddLocomotiveRequest request = new AddLocomotiveRequest()
			.id(locomotive1937Id.id())
			.modelTypeId(locomotiveModelLMSStainierBlack5.id())
			.serialNumber(serialNumberStainier.sn())
			.name("Stainier")
			.gauge(standardGauge.type());

	@Test
	void success() {
		// when
		usecase.execute(request);

		// then
		testEntities.assertState(locomotiveStainier());

		assertThat(events.stream(LocomotiveAddedEvent.class))
				.containsExactly(new LocomotiveAddedEvent(
						locomotive1937Id,
						locomotiveModelLMSStainierBlack5,
						serialNumberStainier,
						"Stainier"));
	}

	@Test
	void throwSerialNumberAlreadyExists() {
		testEntities.save(locomotiveStainier());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(serialNumberAlreadyExists(serialNumberStainier));
	}
}
