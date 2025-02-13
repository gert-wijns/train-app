package be.gert.trainapp.sm.planning._model;

public class TrainLocomotiveDefaults {
	public static final TrainLocomotive orientExpressLocomotive = new TrainLocomotive(
			LocomotiveDefaults.locomotiveOrientExpressId,
			LocomotiveDefaults.locomotiveOrientExpress.serialNumber(),
			true,
			LocomotiveDefaults.locomotiveOrientExpress.decommissioned());
}
