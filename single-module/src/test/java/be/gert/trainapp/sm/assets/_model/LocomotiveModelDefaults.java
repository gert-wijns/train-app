package be.gert.trainapp.sm.assets._model;

import static be.gert.trainapp.sm.assets.LocomotivePowerType.ELECTRIC;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;

import be.gert.trainapp.sm.assets.LocomotiveModelId;

public class LocomotiveModelDefaults {
	// https://www.brdatabase.info/locoqry.php?action=locodata&type=S&id=446003758&loco=5415
	public static final LocomotiveModelId locomotiveModelLMSStainierBlack5Id = new LocomotiveModelId("45415");
	public static final String locomotiveModelLMSStainierBlack5Name = "LMS Stainier Black 5";

	public static LocomotiveModel locomotiveModelLMSStainierBlack5() {
		return new LocomotiveModel(
				locomotiveModelLMSStainierBlack5Id,
				locomotiveModelLMSStainierBlack5Name,
				ELECTRIC,
				standardGauge);
	}

}
