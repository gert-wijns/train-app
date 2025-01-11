package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.PlanningModelExceptions.wagonAlreadyAdded;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.assertTrain;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.WagonDefaults.orientExpressFirstCoach;
import static be.gert.trainapp.sm.planning._model.WagonDefaults.trainOrientExpressFirstCoachId;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;
import static be.gert.trainapp.sm.planning._repository.TrainJpaRepository.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.AddWagonToTrainRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.planning._model.Train;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;

@ModuleTest
class AddWagonToTrainUseCaseTest {
	@Autowired
	TrainJpaRepository jpa;
	@Autowired
	AddWagonToTrainUseCase usecase;

	AddWagonToTrainRequest request = new AddWagonToTrainRequest()
			.trainId(trainOrientExpressId.id())
			.wagonId(trainOrientExpressFirstCoachId.id());
	Train orientExpressWithWagon = emptyOrientExpress()
			.attachWagon(orientExpressFirstCoach());

	@Test
	void success() {
		// given
		jpa.save(emptyOrientExpress());

		// when
		usecase.execute(request);

		// then
		assertTrain(jpa.getById(trainOrientExpressId))
				.isEqualTo(orientExpressWithWagon);
	}

	@Test
	void exceptionWhenAddingSameWagonAgain() {
		// given
		jpa.save(orientExpressWithWagon);

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(wagonAlreadyAdded(
						trainOrientExpressId,
						trainOrientExpressFirstCoachId));
	}

	@Test
	void exceptionWhenTrainNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(trainOrientExpressId));
	}
}
