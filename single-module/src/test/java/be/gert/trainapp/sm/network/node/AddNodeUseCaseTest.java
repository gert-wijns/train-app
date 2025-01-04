package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._model.NodeDefaults.assertNode;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network.node.AddNodeUseCase.alreadyExists;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.AddNodeRequest;
import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;

@ModuleTest
class AddNodeUseCaseTest {
	@Autowired
	NodeJpaRepository jpa;
	@Autowired
	AddNodeUseCase usecase;

	AddNodeRequest request = new AddNodeRequest()
			.id(stationAntwerpId.id())
			.name(stationAntwerp().name())
			.geoPosition(new GeoPositionBody()
					.latitude(stationAntwerp().geoPosition().latitude())
					.longitude(stationAntwerp().geoPosition().longitude()));

	@Test
	void success() {
		// when
		usecase.execute(request);

		// then
		assertNode(jpa.getById(stationAntwerpId))
				.isEqualTo(stationAntwerp());
	}

	@Test
	void throwsAlreadyExists() {
		// given
		jpa.save(stationAntwerp());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(stationAntwerpId));
	}

}
