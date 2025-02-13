package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._repository.NodeJpaRepository.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.RenameNodeRequest;
import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;

@ModuleCoreTest
class RenameNodeUseCaseTest {
	@Autowired
	NodeJpaRepository jpa;
	@Autowired
	RenameNodeUseCase usecase;

	RenameNodeRequest request = new RenameNodeRequest()
			.id(stationAntwerpId.id())
			.newName("Antwerp-Central");

	@Test
	void success() {
		jpa.save(stationAntwerp().toBuilder().name("XX").build());

		// when
		usecase.execute(request);

		// then
		assertEntity(jpa.getById(stationAntwerpId))
				.isEqualTo(stationAntwerp());
	}

	@Test
	void throwsNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(stationAntwerpId));
	}

}
