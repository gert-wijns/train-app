package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.network._model.NetworkDefaults.networkBelgium;
import static be.gert.trainapp.sm.network._model.NetworkDefaults.networkBelgiumId;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.api.network.generated.model.SearchNetworkNodesQueryResponseItem;
import be.gert.trainapp.api.network.generated.model.SearchNetworkNodesQueryResponseItemNetwork;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.network._repository.NetworkJpaRepository;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;
import jakarta.validation.Validator;

@ModuleTest
 class SearchNetworkNodesQueryTest {
	@Autowired
	NetworkJpaRepository networkJpa;
	@Autowired
	NodeJpaRepository jpa;
	@Autowired
	SearchNetworkNodesQuery query;
	@Autowired
	Validator validator;

	@Test
	void selectsWhenQuerying() {
		networkJpa.save(networkBelgium());
		jpa.save(stationAntwerp());

		var result = query.query().getBody();

		assertValid(result);
		assertThat(result).containsExactly(new SearchNetworkNodesQueryResponseItem()
				.id(stationAntwerpId.id())
				.name(stationAntwerp().name())
				.network(new SearchNetworkNodesQueryResponseItemNetwork()
						.id(networkBelgiumId.id())
						.name(networkBelgium().name()))
				.geoPosition(new GeoPositionBody()
						.longitude(stationAntwerp().geoPosition().longitude())
						.latitude(stationAntwerp().geoPosition().latitude())));
	}
}
