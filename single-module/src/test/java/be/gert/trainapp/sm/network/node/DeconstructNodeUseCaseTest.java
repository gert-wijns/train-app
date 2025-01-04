package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._model.NodeExceptions.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.DecommissionNodeRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;

@ModuleTest
class DecommissionNodeUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	DecommissionNodeUseCase usecase;

	DecommissionNodeRequest request = new DecommissionNodeRequest()
			.id(stationAntwerpId.id());

	@Test
	void success() {
		testEntities.save(stationAntwerp());

		// when
		usecase.execute(request);

		// then
		testEntities.assertNotExists(stationAntwerp());
	}

	@Test
	void throwsNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(stationAntwerpId));
	}
}
