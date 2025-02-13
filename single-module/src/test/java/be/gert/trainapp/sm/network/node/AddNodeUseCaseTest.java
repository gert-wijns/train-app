package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.network._model.NetworkDefaults.networkBelgium;
import static be.gert.trainapp.sm.network._model.NetworkDefaults.networkBelgiumId;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._repository.NetworkJpaRepository.notFound;
import static be.gert.trainapp.sm.network.node.AddNodeUseCase.alreadyExists;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.AddNodeRequest;
import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.network._repository.NetworkJpaRepository;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;

@ModuleCoreTest
class AddNodeUseCaseTest {
	@Autowired
	NetworkJpaRepository networkJpa;
	@Autowired
	NodeJpaRepository jpa;
	@Autowired
	AddNodeUseCase usecase;

	AddNodeRequest request = new AddNodeRequest()
			.id(stationAntwerpId.id())
			.name(stationAntwerp().name())
			.networkId(networkBelgiumId.id())
			.geoPosition(new GeoPositionBody()
					.latitude(stationAntwerp().geoPosition().latitude())
					.longitude(stationAntwerp().geoPosition().longitude()));

	@Test
	void success() {
		networkJpa.save(networkBelgium());

		// when
		usecase.execute(request);

		// then
		assertEntity(jpa.getById(stationAntwerpId))
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

	@Test
	void throwsNetworkNotFound() {
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(networkBelgiumId));
	}

}
