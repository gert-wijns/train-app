package be.gert.trainapp.sm.planning.routeplan.model;

import java.time.LocalDate;

import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.planning.RoutePlanId;

public class RoutePlanDestination {
	private RoutePlanId routePlanId;
	private NodeId destinationId;
	private LocalDate latestArrival;
	private String remarks;
}
