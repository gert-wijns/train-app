package be.gert.trainapp.sm.planning.train.model;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.planning.TrainId;

public class TrainExceptions {
	public static DomainException notFound(TrainId trainId) {
		return error("PLANNING_TRAIN_NOT_FOUND",
				"Train not found id '${id}'.")
				.withParam("id", trainId.id())
				.asException();
	}

	public static DomainException alreadyExists(TrainId trainId) {
		return error("PLANNING_TRAIN_ALREADY_EXISTS",
				"Train already exists for id '${id}'.")
				.withParam("id", trainId.id())
				.asException();
	}

	public static DomainException locomotiveAlreadySet(TrainId trainId, LocomotiveId locomotiveId) {
		return error("PLANNING_TRAIN_LOCOMOTIVE_ALREADY_SET",
				"Train '${id}' already has a locomotive with id '${locomotiveId}'.")
				.withParam("id", trainId.id())
				.withParam("locomotiveId", locomotiveId.id())
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
