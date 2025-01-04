package be.gert.trainapp.sm.assets._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;

public class WagonDefaults {
	public static final WagonId wagonId = new WagonId("1");
	public static final WagonModelId wagonModelXs = new WagonModelId("xs");
	public static final SerialNumber serialNumber = new SerialNumber("sn-1");

	public static Wagon testWagon() {
		return new Wagon(
				wagonId,
				wagonModelXs,
				serialNumber,
				standardGauge);
	}

	public static RecursiveComparisonAssert<?> assertWagon(Wagon entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
