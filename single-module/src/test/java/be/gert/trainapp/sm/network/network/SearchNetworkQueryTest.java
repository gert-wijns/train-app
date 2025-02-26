package be.gert.trainapp.sm.network.network;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.network._model.NetworkDefaults.networkBelgium;
import static be.gert.trainapp.sm.network._model.NetworkDefaults.networkBelgiumId;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.SearchNetworkQueryResponseItem;
import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.network._repository.NetworkJpaRepository;

@ModuleCoreTest
class SearchNetworkQueryTest {
	@Autowired
	NetworkJpaRepository jpa;
	@Autowired
	SearchNetworkQuery query;

	@Test
	void selectsWhenQuerying() {
		jpa.save(networkBelgium());

		var result = query.query();

		assertValid(result);
		assertThat(result).containsExactly(new SearchNetworkQueryResponseItem()
				.id(networkBelgiumId.id())
				.name(networkBelgium().name()));
	}
}
