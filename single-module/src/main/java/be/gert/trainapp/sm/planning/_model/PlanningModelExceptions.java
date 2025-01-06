package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.planning.TrainId;

public class PlanningModelExceptions {

	public static DomainException wagonAlreadyAdded(TrainId trainId, WagonId wagonId) {
		return error("PLANNING_TRAIN_WAGON_ALREADY_ADDED",
				"Train '${id}' already has wagon with id '${wagonId}'.")
				.withParam("id", trainId.id())
				.withParam("wagonId", wagonId.id())
				.asException();
	}
}
