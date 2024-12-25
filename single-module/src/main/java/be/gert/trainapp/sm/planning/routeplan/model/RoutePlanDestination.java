package be.gert.trainapp.sm.planning.routeplan.model;

import java.time.LocalDate;

import be.gert.trainapp.sm.network.DestinationId;
import be.gert.trainapp.sm.planning.RoutePlanId;

public class RoutePlanDestination {
	private RoutePlanId routePlanId;
	private DestinationId destinationId;
	private LocalDate latestArrival;
	private String remarks;
}
