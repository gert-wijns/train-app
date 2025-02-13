package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.serialNumberStainier;
import static be.gert.trainapp.sm.assets._model.WagonDefaults.serialNumber;
import static be.gert.trainapp.sm.assets._model.WagonDefaults.testWagon;
import static be.gert.trainapp.sm.assets._model.WagonDefaults.wagonId;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXs;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXsId;
import static be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository.notFound;
import static be.gert.trainapp.sm.assets.wagon.AddWagonUseCase.serialNumberAlreadyExists;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import be.gert.trainapp.api.assets.generated.model.AddWagonRequest;
import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.assets._events.WagonAddedEvent;
import be.gert.trainapp.sm.assets._repository.WagonJpaRepository;
import be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository;

@ModuleCoreTest
class AddWagonUseCaseTest {
	@Autowired
	WagonModelJpaRepository modelJpa;
	@Autowired
	WagonJpaRepository jpa;
	@Autowired
	ApplicationEvents events;
	@Autowired
	AddWagonUseCase usecase;

	AddWagonRequest request = new AddWagonRequest()
			.wagonId(wagonId.id())
			.modelTypeId(wagonModelXsId.id())
			.serialNumber(serialNumber.sn());

	@Test
	void success() {
		modelJpa.save(wagonModelXs());
		usecase.execute(request);

		assertEntity(jpa.getById(wagonId))
				.isEqualTo(testWagon());
		assertThat(events.stream(WagonAddedEvent.class))
				.containsExactly(new WagonAddedEvent(wagonId, wagonModelXsId, serialNumber));
	}

	@Test
	void throwModelNotFound() {
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(wagonModelXsId));
	}


	@Test
	void throwSerialNumberAlreadyExists() {
		jpa.save(testWagon());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(serialNumberAlreadyExists(serialNumberStainier));
	}
}
