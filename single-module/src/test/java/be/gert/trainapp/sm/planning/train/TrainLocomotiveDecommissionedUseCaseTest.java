package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.TrainDefaults.assertTrain;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.locomotiveOrientExpressId;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.orientExpressLocomotive;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AopTestUtils;

import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets._events.LocomotiveDecommissioned;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;

@ModuleTest
class TrainLocomotiveDecommissionedUseCaseTest {
	@Autowired
	TrainJpaRepository jpa;
	@Autowired
	TrainLocomotiveDecommissionedUseCase usecase;

	LocomotiveDecommissioned event = new LocomotiveDecommissioned(locomotiveOrientExpressId);

	@BeforeEach
	void setup() {
		// strip Transactional.NEW by ApplicationModuleListener for test...
		usecase = AopTestUtils.getTargetObject(usecase);
	}

	@Test
	void success() {
		// given
		jpa.save(emptyOrientExpress());

		// when
		usecase.onLocomotiveDecommissioned(event);

		// then
		assertTrain(jpa.getById(trainOrientExpressId))
				.isEqualTo(emptyOrientExpress().toBuilder()
						.containsDecommissioned(true)
						.locomotive(orientExpressLocomotive.withDecommissioned(true)));
	}

}
