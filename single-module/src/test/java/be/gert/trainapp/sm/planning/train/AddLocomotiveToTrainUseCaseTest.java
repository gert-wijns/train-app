package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning.given.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning.given.TrainDefaults.locomotiveOrientExpressId;
import static be.gert.trainapp.sm.planning.given.TrainDefaults.trainOrientExpressId;
import static be.gert.trainapp.sm.planning.train.model.TrainExceptions.locomotiveAlreadySet;
import static be.gert.trainapp.sm.planning.train.model.TrainExceptions.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.AddLocomotiveToTrainRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;

@ModuleTest
class AddLocomotiveToTrainUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	AddLocomotiveToTrainUseCase usecase;

	AddLocomotiveToTrainRequest request = new AddLocomotiveToTrainRequest()
			.trainId(trainOrientExpressId.id())
			.locomotiveId(locomotiveOrientExpressId.id());

	@Test
	void success() {
		// given
		testEntities.save(emptyOrientExpress());

		// when
		usecase.execute(request);

		// then
		testEntities.assertState(emptyOrientExpress().withLocomotiveId(locomotiveOrientExpressId));
	}

	@Test
	void exceptionWhenSettingLocomotiveAgain() {
		// given
		testEntities.save(emptyOrientExpress().withLocomotiveId(locomotiveOrientExpressId));

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
