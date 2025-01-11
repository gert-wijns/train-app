package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.planning._model.TrainEngineerDefaults.trainEngineer;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.SearchTrainEngineersResponseItem;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.planning._repository.TrainEngineerJpaRepository;

@ModuleTest
class SearchTrainEngineersQueryTest {
	@Autowired
	TrainEngineerJpaRepository jpa;
	@Autowired
	SearchTrainEngineersQuery query;

	@Test
	void selects() {
		// given
		jpa.save(trainEngineer());

		// when
		var result = query.query().getBody();

		// then
		assertValid(result);
		assertThat(result)
				.containsExactly(new SearchTrainEngineersResponseItem()
						.id(employeeChristineGonzalesId.id())
						.active(true));
	}
}
