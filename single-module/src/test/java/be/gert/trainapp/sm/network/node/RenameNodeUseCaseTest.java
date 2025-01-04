package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._model.NodeExceptions.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.RenameNodeRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;

@ModuleTest
class RenameNodeUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	RenameNodeUseCase usecase;

	RenameNodeRequest request = new RenameNodeRequest()
			.id(stationAntwerpId.id())
			.newName("Antwerp-Central");

	@Test
	void success() {
		testEntities.save(stationAntwerp().toBuilder().name("XX").build());

		// when
		usecase.execute(request);

		// then
		testEntities.assertState(stationAntwerp());
	}

	@Test
	void throwsNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(stationAntwerpId));
	}

}
