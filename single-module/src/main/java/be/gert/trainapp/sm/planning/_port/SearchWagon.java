package be.gert.trainapp.sm.planning._port;

import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.planning._model.Wagon;

public interface SearchWagon {
	Wagon getById(WagonId id);
}
