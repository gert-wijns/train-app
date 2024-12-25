package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.sm.assets.given.WagonDefaults.wagonId;
import static be.gert.trainapp.sm.assets.given.WagonDefaults.wagonModelXs;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import be.gert.trainapp.api.assets.generated.model.AddWagonRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets.given.WagonDefaults;
import be.gert.trainapp.sm.assets.wagon.model.Wagon;
import be.gert.trainapp.sm.assets.wagon.model.events.WagonAddedEvent;

@ModuleTest
class AddWagonUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	ApplicationEvents events;
	@Autowired
	AddWagonUseCase usecase;

	AddWagonRequest request = new AddWagonRequest()
			.wagonId(wagonId.id())
			.modelTypeId(wagonModelXs.id());

	@Test
	void success() {
		usecase.execute(request);

		testEntities.assertState(new Wagon(wagonId, wagonModelXs));
		assertThat(events.stream(WagonAddedEvent.class))
				.containsExactly(new WagonAddedEvent(wagonId, wagonModelXs));
	}

}
