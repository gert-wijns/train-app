package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network.given.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network.given.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network.given.NodeDefaults.stationBrusselsGeoPosition;
import static be.gert.trainapp.sm.network.node.model.NodeExceptions.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.api.network.generated.model.RepositionNodeRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;

@ModuleTest
class RepositionNodeUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	RepositionNodeUseCase usecase;

	RepositionNodeRequest request = new RepositionNodeRequest()
			.id(stationAntwerpId.id())
			.newGeoPosition(new GeoPositionBody()
					.longitude(stationBrusselsGeoPosition.longitude())
					.latitude(stationBrusselsGeoPosition.latitude()));

	@Test
	void success() {
		testEntities.save(stationAntwerp());

		// when
		usecase.execute(request);

		// then
		testEntities.assertState(stationAntwerp().withGeoPosition(stationBrusselsGeoPosition));
	}

	@Test
	void throwsNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(stationAntwerpId));
	}

}
