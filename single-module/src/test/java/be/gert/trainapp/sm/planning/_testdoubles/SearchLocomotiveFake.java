package be.gert.trainapp.sm.planning._testdoubles;

import static be.gert.trainapp.sm.planning._model.LocomotiveDefaults.locomotiveOrientExpress;
import static be.gert.trainapp.sm.planning._model.LocomotiveDefaults.locomotiveOrientExpressId;

import be.gert.trainapp.sm._shared.testdoubles.ModuleTestDouble;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.planning._port.SearchLocomotive;
import be.gert.trainapp.sm.planning._model.Locomotive;

@ModuleTestDouble
public class SearchLocomotiveFake implements SearchLocomotive {
	@Override
	public Locomotive getById(LocomotiveId id) {
		if (locomotiveOrientExpressId.equals(id)) {
			return locomotiveOrientExpress;
		}
		return null;
	}
}
