package be.gert.trainapp.sm.assets.locomotive;

import static be.gert.trainapp.sm.assets.given.LocomotiveDefaults.locomotive1937Id;
import static be.gert.trainapp.sm.assets.given.LocomotiveDefaults.locomotiveModelLMSStainierBlack5;
import static be.gert.trainapp.sm.assets.given.LocomotiveDefaults.serialNumberStainier;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import be.gert.trainapp.api.assets.generated.model.AddLocomotiveRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;
import be.gert.trainapp.sm.assets.locomotive.model.Locomotive;
import be.gert.trainapp.sm.assets.locomotive.model.events.LocomotiveAddedEvent;

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
			.name("Stainier");

	@Test
	void success() {
		// when
		usecase.execute(request);

		// then
		testEntities.assertState(new Locomotive(
				locomotive1937Id,
				locomotiveModelLMSStainierBlack5,
				"Stainier",
				serialNumberStainier));

		assertThat(events.stream(LocomotiveAddedEvent.class))
				.containsExactly(new LocomotiveAddedEvent(
						locomotive1937Id,
						locomotiveModelLMSStainierBlack5,
						serialNumberStainier,
						"Stainier"));
	}

}
