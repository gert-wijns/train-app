package be.gert.trainapp.sm.planning._testdoubles;

import static be.gert.trainapp.sm.planning._model.WagonDefaults.orientExpressFirstCoach;

import be.gert.trainapp.sm._shared.testdoubles.ModuleTestDouble;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.planning._port.SearchWagon;
import be.gert.trainapp.sm.planning._model.Wagon;

@ModuleTestDouble
public class SearchWagonFake implements SearchWagon {
	@Override
	public Wagon getById(WagonId id) {
		if (orientExpressFirstCoach.id().equals(id)) {
			return orientExpressFirstCoach;
		}
		return null;
	}
}
