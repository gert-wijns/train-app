package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.Train.locomotiveAlreadySet;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.assertTrain;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.locomotiveOrientExpressId;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;
import static be.gert.trainapp.sm.planning._repository.TrainJpaRepository.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.AddLocomotiveToTrainRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;

@ModuleTest
class AddLocomotiveToTrainUseCaseTest {
	@Autowired
	TrainJpaRepository jpa;
	@Autowired
	AddLocomotiveToTrainUseCase usecase;

	AddLocomotiveToTrainRequest request = new AddLocomotiveToTrainRequest()
			.trainId(trainOrientExpressId.id())
			.locomotiveId(locomotiveOrientExpressId.id());

	@Test
	void success() {
		// given
		jpa.save(emptyOrientExpress().toBuilder()
				.locomotive(null)
				.build());

		// when
		usecase.execute(request);

		// then
		assertTrain(jpa.getById(trainOrientExpressId))
				.isEqualTo(emptyOrientExpress());
	}

	@Test
	void exceptionWhenSettingLocomotiveAgain() {
		// given
		jpa.save(emptyOrientExpress());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(locomotiveAlreadySet(trainOrientExpressId, locomotiveOrientExpressId));
	}

	@Test
	void exceptionWhenTrainNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(trainOrientExpressId));
	}
}
