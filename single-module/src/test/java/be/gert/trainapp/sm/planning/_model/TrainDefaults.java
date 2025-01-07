package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.planning.TrainId;

public class TrainDefaults {
	public static final TrainId trainOrientExpressId = new TrainId("OrientExpress");
	public static final WagonId trainOrientExpressFirstCoachId = new WagonId("OrientExpress-firstCoach");
	public static final LocomotiveId locomotiveOrientExpressId = new LocomotiveId("OrientExpress-LocomotiveId");
	public static final WagonModelId wagonModelTypeId = new WagonModelId("wagon-model-123");
	public static final TrainLocomotive orientExpressLocomotive = new TrainLocomotive(
			locomotiveOrientExpressId,
			new SerialNumber("orientExpressLocomotive-sn"),
			true,
			false);

	public static Wagon orientExpressFirstCoach() {
		return new Wagon(trainOrientExpressFirstCoachId,
				new SerialNumber("trainOrientExpressFirstCoach-sn"),
				standardGauge, false);
	}

	public static Train emptyOrientExpress() {
		return Train.builder()
				.id(trainOrientExpressId)
				.locomotive(orientExpressLocomotive)
				.gauge(standardGauge)
				.wagons(new ArrayList<>())
				.build();
	}

	public static RecursiveComparisonAssert<?> assertTrain(Train entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS)
				.ignoringFieldsMatchingRegexes("wagons\\.train\\.(?!id).*");
	}

}
