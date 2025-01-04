package be.gert.trainapp.sm.assets._model;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.SerialNumber;

public class WagonExceptions {

	public static DomainException serialNumberAlreadyExists(SerialNumber serialNumber) {
		return error("ASSETS_WAGON_SERAL_NUMBER_ALREADY_EXISTS",
				"Wagon with Serial Number '${serialNumber}' already exists .")
				.withParam("serialNumber", serialNumber.sn())
				.asException();
	}

}
