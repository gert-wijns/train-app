package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.WagonDefaults.orientExpressFirstCoach;
import static be.gert.trainapp.sm.planning._model.WagonDefaults.trainOrientExpressFirstCoachId;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AopTestUtils;

import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets._events.WagonDecommissioned;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;

@ModuleTest
class TrainWagonDecommissionedUseCaseTest {
	@Autowired
	TrainJpaRepository jpa;
	@Autowired
	TrainWagonDecommissionedUseCase usecase;

	WagonDecommissioned event = new WagonDecommissioned(trainOrientExpressFirstCoachId);

	@Test
	void success() {
		// given
		jpa.save(emptyOrientExpress().attachWagon(orientExpressFirstCoach()));

		// when
		TrainWagonDecommissionedUseCase unproxied = AopTestUtils.getTargetObject(this.usecase);
		unproxied.onWagonDecommissioned(event);

		// then
		assertThat(jpa.getById(trainOrientExpressId).wagons().getFirst())
				.extracting("decommissioned")
				.isEqualTo(true);
	}

}
