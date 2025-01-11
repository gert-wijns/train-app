package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._repository.NodeJpaRepository.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.DecommissionNodeRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;

@ModuleTest
class DecommissionNodeUseCaseTest {
	@Autowired
	NodeJpaRepository jpa;
	@Autowired
	DecommissionNodeUseCase usecase;

	DecommissionNodeRequest request = new DecommissionNodeRequest()
			.id(stationAntwerpId.id());

	@Test
	void success() {
		jpa.save(stationAntwerp());

		// when
		usecase.execute(request);

		// then
		assertEntity(jpa.getById(stationAntwerpId))
				.isEqualTo(stationAntwerp().toBuilder()
						.decommissioned(true)
						.build());
	}

	@Test
	void throwsNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(stationAntwerpId));
	}
}
