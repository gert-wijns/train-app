package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm.assets.LocomotivePowerType.ELECTRIC;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.SerialNumber;

public class LocomotiveDefaults {
	public static final LocomotiveId locomotiveOrientExpressId = new LocomotiveId("OrientExpress-LocomotiveId");

	public static final Locomotive locomotiveOrientExpress = new Locomotive(
			locomotiveOrientExpressId,
			new SerialNumber("orientExpressLocomotive-sn"),
			ELECTRIC,
			standardGauge,
			false);
}
