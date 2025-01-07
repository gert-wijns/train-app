package be.gert.trainapp.sm.network._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.network.NetworkId;

public class NetworkDefaults {
	public static NetworkId networkBelgiumId = new NetworkId("network-be");

	public static Network networkBelgium() {
		return Network.builder()
				.id(networkBelgiumId)
				.name("Belgium")
				.build();
	}

	public static RecursiveComparisonAssert<?> assertNetwork(Network entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
