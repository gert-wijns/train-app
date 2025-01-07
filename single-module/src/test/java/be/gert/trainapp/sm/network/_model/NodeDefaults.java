package be.gert.trainapp.sm.network._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static be.gert.trainapp.sm.network._model.NetworkDefaults.networkBelgiumId;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm._shared.values.GeoPosition;
import be.gert.trainapp.sm.network.NodeId;

public class NodeDefaults {
	public static final NodeId stationAntwerpId = new NodeId("station-antwerp-central-id");
	public static final NodeId stationBrusselsId = new NodeId("station-brussels-central-id");
	public static final GeoPosition stationAntwerpGeoPosition = new GeoPosition(new BigDecimal(1), new BigDecimal(5));
	public static final GeoPosition stationBrusselsGeoPosition = new GeoPosition(new BigDecimal(2), new BigDecimal(10));

	public static Node stationAntwerp() {
		return Node.builder()
				.id(stationAntwerpId)
				.networkId(networkBelgiumId)
				.geoPosition(stationAntwerpGeoPosition)
				.name("Antwerp-Central")
				.build();
	}

	public static Node stationBrussels() {
		return Node.builder()
				.id(stationBrusselsId)
				.geoPosition(stationBrusselsGeoPosition)
				.name("Brussels-Central")
				.build();
	}

	public static RecursiveComparisonAssert<?> assertNode(Node entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
