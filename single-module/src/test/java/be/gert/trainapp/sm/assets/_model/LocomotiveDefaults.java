package be.gert.trainapp.sm.assets._model;

import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.SerialNumber;

public class LocomotiveDefaults {
	public static final LocomotiveId locomotive1937Id = new LocomotiveId("locomotive-1");

	public static final SerialNumber serialNumberStainier = new SerialNumber("Stainier-SN");

	// https://www.brdatabase.info/locoqry.php?action=locodata&type=S&id=446003758&loco=5415
	public static final LocomotiveModelId locomotiveModelLMSStainierBlack5 = new LocomotiveModelId("45415");

	public static Locomotive locomotiveStainier() {
		return new Locomotive(
				locomotive1937Id,
				locomotiveModelLMSStainierBlack5,
				"Stainier",
				serialNumberStainier,
				standardGauge);
	}
}
