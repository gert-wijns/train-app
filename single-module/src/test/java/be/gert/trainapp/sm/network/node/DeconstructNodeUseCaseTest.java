package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network.given.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network.given.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network.node.model.NodeExceptions.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.DeconstructNodeRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;

@ModuleTest
class DeconstructNodeUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	DeconstructNodeUseCase usecase;

	DeconstructNodeRequest request = new DeconstructNodeRequest()
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
