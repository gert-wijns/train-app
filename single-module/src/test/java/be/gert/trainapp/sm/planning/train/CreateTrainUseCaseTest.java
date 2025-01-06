package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.assertTrain;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.locomotiveOrientExpressId;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.orientExpressLocomotive;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;
import static be.gert.trainapp.sm.planning.train.CreateTrainUseCase.alreadyExists;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.CreateTrainRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.planning._model.Train;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;

@ModuleTest
class CreateTrainUseCaseTest {
	@Autowired
	TrainJpaRepository jpa;
	@Autowired
	CreateTrainUseCase usecase;

	CreateTrainRequest request = new CreateTrainRequest()
			.trainId(trainOrientExpressId.id())
			.locomotiveId(locomotiveOrientExpressId.id());

	@Test
	void success() {
		// when
		usecase.execute(request);

		// then
		assertTrain(jpa.getById(trainOrientExpressId))
				.isEqualTo(Train.builder()
						.id(trainOrientExpressId)
						.locomotive(orientExpressLocomotive)
						.gauge(standardGauge)
						.build());
	}

	@Test
	void exceptionWhenAlreadyExists() {
		// given
		jpa.save(emptyOrientExpress());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(trainOrientExpressId));
	}
}
