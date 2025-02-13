package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.assets._model.WagonDefaults.testWagon;
import static be.gert.trainapp.sm.assets._model.WagonDefaults.wagonId;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXs;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import be.gert.trainapp.api.assets.generated.model.DecommissionWagonRequest;
import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.assets._events.WagonDecommissioned;
import be.gert.trainapp.sm.assets._repository.WagonJpaRepository;
import be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository;

@ModuleCoreTest
class DecommissionWagonUseCaseTest {
	@Autowired
	WagonModelJpaRepository modelJpa;
	@Autowired
	WagonJpaRepository jpa;
	@Autowired
	ApplicationEvents events;
	@Autowired
	DecommissionWagonUseCase usecase;

	DecommissionWagonRequest request = new DecommissionWagonRequest()
			.id(wagonId.id());

	@Test
	void success() {
		// given
		modelJpa.save(wagonModelXs());
		jpa.save(testWagon());

		// when
		usecase.execute(request);

		// then
		assertEntity(jpa.getById(wagonId))
				.isEqualTo(testWagon().toBuilder()
						.decommissioned(true)
						.build());

		assertThat(events.stream(WagonDecommissioned.class))
				.containsExactly(new WagonDecommissioned(wagonId));
	}

}
