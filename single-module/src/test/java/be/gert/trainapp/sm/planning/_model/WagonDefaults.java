package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;

import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;

public class WagonDefaults {
	public static final WagonId trainOrientExpressFirstCoachId = new WagonId("OrientExpress-firstCoach");
	public static final WagonModelId wagonModelTypeId = new WagonModelId("wagon-model-123");

	public static Wagon orientExpressFirstCoach = new Wagon(trainOrientExpressFirstCoachId,
				new SerialNumber("trainOrientExpressFirstCoach-sn"),
				standardGauge, false);
}
