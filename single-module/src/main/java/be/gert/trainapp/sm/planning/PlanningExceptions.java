package be.gert.trainapp.sm.planning;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.network.TrackGauge;

public class PlanningExceptions {

	public static DomainException trainLocomotiveAlreadySet(TrainId trainId, LocomotiveId locomotiveId) {
		return error("PLANNING_TRAIN_LOCOMOTIVE_ALREADY_SET",
				"Train '${id}' already has a locomotive with id '${locomotiveId}'.")
				.withParam("id", trainId.id())
				.withParam("locomotiveId", locomotiveId.id())
				.asException();
	}

	public static DomainException trainLocomotiveGaugeNotCompatible(TrainId trainId, TrackGauge trainGauge,
	                                    LocomotiveId locomotiveId, TrackGauge locomotiveGauge) {
		return error("PLANNING_TRAIN_LOCOMOTIVE_GAUGE_NOT_COMPATIBLE",
				"Train '${id}' needs gauge '${trainGauge}' but locomotive '${locomotiveId}' has gauge '${locomotiveGauge}.")
				.withParam("id", trainId.id())
				.withParam("trainGauge", trainGauge.type())
				.withParam("locomotiveId", locomotiveId.id())
				.withParam("locomotiveGauge", locomotiveGauge.type())
				.asException();
	}

	public static DomainException wagonAlreadyAdded(TrainId trainId, WagonId wagonId) {
		return error("PLANNING_TRAIN_WAGON_ALREADY_ADDED",
				"Train '${id}' already has wagon with id '${wagonId}'.")
				.withParam("id", trainId.id())
				.withParam("wagonId", wagonId.id())
				.asException();
	}
}
