package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressFirstCoachId;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;
import static be.gert.trainapp.sm.planning._model.TrainExceptions.notFound;
import static be.gert.trainapp.sm.planning._model.TrainExceptions.wagonAlreadyAdded;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.AddWagonToTrainRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;
import be.gert.trainapp.sm.planning._model.Train;

@ModuleTest
class AddWagonToTrainUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	AddWagonToTrainUseCase usecase;

	AddWagonToTrainRequest request = new AddWagonToTrainRequest()
			.trainId(trainOrientExpressId.id())
			.wagonId(trainOrientExpressFirstCoachId.id());
	Train orientExpressWithWagon = emptyOrientExpress().toBuilder()
			.wagonIds(List.of(trainOrientExpressFirstCoachId))
			.build();

	@Test
	void success() {
		// given
		testEntities.save(emptyOrientExpress());

		// when
		usecase.execute(request);

		// then
		testEntities.assertState(orientExpressWithWagon);
	}

	@Test
	void exceptionWhenAddingSameWagonAgain() {
		// given
		testEntities.save(orientExpressWithWagon);

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
