package be.gert.trainapp.sm.assets._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.assets.WagonModelId;

public class WagonModelDefaults {

	public static final WagonModelId wagonModelXsId = new WagonModelId("xs");
	public static final String wagonModelXsName = "WagonModel-name";

	public static WagonModel wagonModelXs() {
		return WagonModel.builder()
				.id(wagonModelXsId)
				.name(wagonModelXsName)
				.gauge(standardGauge)
				.build();
	}

	public static RecursiveComparisonAssert<?> assertWagonModel(WagonModel entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
