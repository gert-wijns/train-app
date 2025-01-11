package be.gert.trainapp.sm.assets.locomotive;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.locomotive1937Id;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.locomotiveStainier;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import be.gert.trainapp.api.assets.generated.model.DecommissionLocomotiveRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets._events.LocomotiveDecommissioned;
import be.gert.trainapp.sm.assets._repository.LocomotiveJpaRepository;
import be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository;

@ModuleTest
class DecommissionLocomotiveUseCaseTest {
	@Autowired
	LocomotiveModelJpaRepository modelJpa;
	@Autowired
	LocomotiveJpaRepository jpa;
	@Autowired
	ApplicationEvents events;
	@Autowired
	DecommissionLocomotiveUseCase usecase;

	DecommissionLocomotiveRequest request = new DecommissionLocomotiveRequest()
			.id(locomotive1937Id.id());

	@Test
	void success() {
		// given
		modelJpa.save(locomotiveModelLMSStainierBlack5());
		jpa.save(locomotiveStainier());

		// when
		usecase.execute(request);

		// then
		assertEntity(jpa.getById(locomotive1937Id))
				.isEqualTo(locomotiveStainier().toBuilder()
						.decommissioned(true)
						.build());

		assertThat(events.stream(LocomotiveDecommissioned.class))
				.containsExactly(new LocomotiveDecommissioned(locomotive1937Id));
	}

}
