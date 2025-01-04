package be.gert.trainapp.sm.planning._model;

import java.time.OffsetDateTime;
import java.util.List;

import be.gert.trainapp.sm.planning.TrainId;

public class RoutePlan {
	private List<RoutePlanDestination> destinations;
	private OffsetDateTime startTime;
	private TrainId trainId;
	private boolean approved;

}
