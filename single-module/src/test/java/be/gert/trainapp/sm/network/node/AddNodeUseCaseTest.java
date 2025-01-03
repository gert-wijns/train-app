package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network.given.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network.given.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network.node.model.NodeExceptions.alreadyExists;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.AddNodeRequest;
import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;

@ModuleTest
class AddNodeUseCaseTest {
	@Autowired
	TestEntities testEntities;
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
		testEntities.assertState(stationAntwerp());
	}

	@Test
	void throwsAlreadyExists() {
		// given
		testEntities.save(stationAntwerp());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(stationAntwerpId));
	}

}
