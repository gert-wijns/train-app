package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.sm.assets.given.LocomotiveDefaults.serialNumberStainier;
import static be.gert.trainapp.sm.assets.given.WagonDefaults.serialNumber;
import static be.gert.trainapp.sm.assets.given.WagonDefaults.testWagon;
import static be.gert.trainapp.sm.assets.given.WagonDefaults.wagonId;
import static be.gert.trainapp.sm.assets.given.WagonDefaults.wagonModelXs;
import static be.gert.trainapp.sm.assets.wagon.model.WagonExceptions.serialNumberAlreadyExists;
import static be.gert.trainapp.sm.network.given.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import be.gert.trainapp.api.assets.generated.model.AddWagonRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;
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
			.modelTypeId(wagonModelXs.id())
			.serialNumber(serialNumber.sn())
			.gauge(standardGauge.type());

	@Test
	void success() {
		usecase.execute(request);

		testEntities.assertState(testWagon());
		assertThat(events.stream(WagonAddedEvent.class))
				.containsExactly(new WagonAddedEvent(wagonId, wagonModelXs, serialNumber));
	}

	@Test
	void throwSerialNumberAlreadyExists() {
		testEntities.save(testWagon());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(serialNumberAlreadyExists(serialNumberStainier));
	}
}
