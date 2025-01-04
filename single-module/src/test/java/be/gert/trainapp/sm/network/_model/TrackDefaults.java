package be.gert.trainapp.sm.network._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationBrusselsId;
import static be.gert.trainapp.sm.network._model.SpeedDefaults.kilometersPerHour;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.network.TrackId;

public class TrackDefaults {

	public static final TrackId trackAntwerpBrusselsId = new TrackId(stationAntwerpId, stationBrusselsId);
	public static final TrackGauge standardGauge = new TrackGauge("1435mm");

	public static Track trackAntwerpBrussels() {
		return new Track(trackAntwerpBrusselsId,
				true,
				new BigDecimal(3).setScale(2, RoundingMode.UP),
				kilometersPerHour(50),
				standardGauge,
				false);
	}

	public static RecursiveComparisonAssert<?> assertTrack(Track entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
