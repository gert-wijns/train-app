package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.planning._model.Train.trainEngineerNotActive;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.assertTrain;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;
import static be.gert.trainapp.sm.planning._model.TrainEngineerDefaults.trainEngineer;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.BoardTrainEngineerToTrainRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.planning._repository.TrainEngineerJpaRepository;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;

@ModuleTest
class BoardTrainEngineerToTrainUseCaseTest {
	@Autowired
	TrainJpaRepository jpa;
	@Autowired
	TrainEngineerJpaRepository trainEngineerJpa;
	@Autowired
	BoardTrainEngineerToTrainUseCase usecase;

	BoardTrainEngineerToTrainRequest request = new BoardTrainEngineerToTrainRequest()
			.trainId(trainOrientExpressId.id())
			.employeeId(employeeChristineGonzalesId.id());

	@Test
	void success() {
		// given
		jpa.save(emptyOrientExpress());
		trainEngineerJpa.save(trainEngineer());

		// when
		usecase.execute(request);

		// then
		assertTrain(jpa.getById(trainOrientExpressId))
				.isEqualTo(emptyOrientExpress().toBuilder()
						.trainEngineer(employeeChristineGonzalesId)
						.build());
	}

	@Test
	void throwsWhenTrainEngineerNotActive() {
		// given
		jpa.save(emptyOrientExpress());
		trainEngineerJpa.save(trainEngineer().toBuilder().active(false).build());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(trainEngineerNotActive(employeeChristineGonzalesId));
	}

	@Test
	void throwsWhenTrainEngineerNotFound() {
		// given
		jpa.save(emptyOrientExpress());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(TrainEngineerJpaRepository.notFound(employeeChristineGonzalesId));
	}

	@Test
	void throwsWhenTrainNotFound() {
		// given
		trainEngineerJpa.save(trainEngineer());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(TrainJpaRepository.notFound(trainOrientExpressId));
	}
}
