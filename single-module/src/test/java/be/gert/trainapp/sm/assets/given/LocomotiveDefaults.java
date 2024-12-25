package be.gert.trainapp.sm.assets.given;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;

public interface LocomotiveDefaults {
	LocomotiveId locomotive1937Id = new LocomotiveId("locomotive-1");

	// https://www.brdatabase.info/locoqry.php?action=locodata&type=S&id=446003758&loco=5415
	LocomotiveModelId locomotiveModelLMSStainierBlack5 = new LocomotiveModelId("45415");
}
