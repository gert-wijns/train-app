package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;

import java.util.ArrayList;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.planning.TrainId;

public class TrainDefaults {
	public static final TrainId trainOrientExpressId = new TrainId("OrientExpress");

	public static Train emptyOrientExpress() {
		return new Train(
				trainOrientExpressId,
				TrainLocomotiveDefaults.orientExpressLocomotive,
				null,
				new ArrayList<>(),
				standardGauge,
				null,
				null,
				false);
	}

	public static RecursiveComparisonAssert<?> assertTrain(Train entity) {
		return assertEntity(entity)
				.ignoringFieldsMatchingRegexes("wagons\\.train\\.(?!id).*");
	}

}
