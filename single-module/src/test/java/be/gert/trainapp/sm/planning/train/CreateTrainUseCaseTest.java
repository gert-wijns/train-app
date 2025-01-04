package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;
import static be.gert.trainapp.sm.planning._model.TrainExceptions.alreadyExists;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.CreateTrainRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;
import be.gert.trainapp.sm.planning._model.Train;

@ModuleTest
class CreateTrainUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	CreateTrainUseCase usecase;

	CreateTrainRequest request = new CreateTrainRequest()
			.trainId(trainOrientExpressId.id());

	@Test
	void success() {
		// when
		usecase.execute(request);

		// then
		testEntities.assertState(Train.builder().id(trainOrientExpressId).build());
	}

	@Test
	void exceptionWhenAlreadyExists() {
		// given
		testEntities.save(emptyOrientExpress());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(trainOrientExpressId));
	}
}
