package be.gert.trainapp.sm.planning._model;

import be.gert.trainapp.sm.assets.SerialNumber;

public class TrainLocomotiveDefaults {
	public static final TrainLocomotive orientExpressLocomotive = new TrainLocomotive(
			LocomotiveDefaults.locomotiveOrientExpressId,
			new SerialNumber("orientExpressLocomotive-sn"),
			true,
			false);
}
