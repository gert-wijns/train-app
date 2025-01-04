package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerp;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.api.network.generated.model.SearchNetworkNodesQueryResponseItem;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.network._repository.NodeJpaRepository;

@ModuleTest
 class SearchNetworkNodesQueryTest {
	@Autowired
	NodeJpaRepository jpa;
	@Autowired
	SearchNetworkNodesQuery query;

	@Test
	void selectsWhenQuerying() {
		jpa.save(stationAntwerp());

		var result = query.query().getBody();

		assertThat(result).containsExactly(new SearchNetworkNodesQueryResponseItem()
				.id(stationAntwerpId.id())
				.name(stationAntwerp().name())
				.geoPosition(new GeoPositionBody()
						.longitude(stationAntwerp().geoPosition().longitude())
						.latitude(stationAntwerp().geoPosition().latitude())));
	}
}
