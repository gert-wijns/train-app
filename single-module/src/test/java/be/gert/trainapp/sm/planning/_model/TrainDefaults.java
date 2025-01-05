package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.planning.TrainId;

public class TrainDefaults {
	public static final TrainId trainOrientExpressId = new TrainId("OrientExpress");
	public static final WagonId trainOrientExpressFirstCoachId = new WagonId("OrientExpress-firstCoach");
	public static final LocomotiveId locomotiveOrientExpressId = new LocomotiveId("OrientExpress-LocomotiveId");
	public static final WagonModelId wagonModelTypeId = new WagonModelId("wagon-model-123");

	public static TrainWagon orientExpressFirstCoach() {
		return TrainWagon.builder()
				.id(trainOrientExpressFirstCoachId)
				.modelId(wagonModelTypeId)
				.build();
	}

	public static TrainLocomotive orientExpressLocomotive() {
		return TrainLocomotive.builder()
				.id(locomotiveOrientExpressId)
				.modelId(new LocomotiveModelId("model-123"))
				.build();
	}

	public static Train emptyOrientExpress() {
		Train train = Train.builder()
				.id(trainOrientExpressId)
				.wagons(new ArrayList<>())
				.build();
		train.usingLocomotive(orientExpressLocomotive());
		return train;
	}

	public static RecursiveComparisonAssert<?> assertTrain(Train entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS)
				.ignoringFieldsMatchingRegexes("locomotive\\.train\\.(?!id).*")
				.ignoringFieldsMatchingRegexes("wagons\\.train\\.(?!id).*");
	}

}
