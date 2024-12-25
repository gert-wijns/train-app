package be.gert.trainapp.sm.planning.given;

import java.util.ArrayList;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning.train.model.Train;

public class TrainDefaults {

	public static final TrainId trainOrientExpressId = new TrainId("OrientExpress");
	public static final WagonId trainOrientExpressFirstCoachId = new WagonId("OrientExpress-firstCoach");
	public static final LocomotiveId locomotiveOrientExpressId = new LocomotiveId("OrientExpress-LocomotiveId");
	public static Train emptyOrientExpress() {
		return new Train(trainOrientExpressId,null, new ArrayList<>(),false);
	}
}
