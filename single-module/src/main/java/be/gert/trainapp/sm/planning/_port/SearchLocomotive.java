package be.gert.trainapp.sm.planning._port;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.planning._model.Locomotive;

public interface SearchLocomotive {
	Locomotive getById(LocomotiveId id);
}
