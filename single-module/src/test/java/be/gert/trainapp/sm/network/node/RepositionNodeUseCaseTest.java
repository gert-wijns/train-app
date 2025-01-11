package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationBrusselsGeoPosition;
import static be.gert.trainapp.sm.network._repository.NodeJpaRepository.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.api.network.generated.model.RepositionNodeRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;

@ModuleTest
class RepositionNodeUseCaseTest {
	@Autowired
	NodeJpaRepository jpa;
	@Autowired
	RepositionNodeUseCase usecase;

	RepositionNodeRequest request = new RepositionNodeRequest()
			.id(stationAntwerpId.id())
			.newGeoPosition(new GeoPositionBody()
					.longitude(stationBrusselsGeoPosition.longitude())
					.latitude(stationBrusselsGeoPosition.latitude()));

	@Test
	void success() {
		jpa.save(stationAntwerp());

		// when
		usecase.execute(request);

		// then
		assertEntity(jpa.getById(stationAntwerpId))
				.isEqualTo(stationAntwerp().toBuilder()
						.geoPosition(stationBrusselsGeoPosition)
						.build());
	}

	@Test
	void throwsNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(stationAntwerpId));
	}

}
