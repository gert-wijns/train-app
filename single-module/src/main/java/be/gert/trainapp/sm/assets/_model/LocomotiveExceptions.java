package be.gert.trainapp.sm.assets._model;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.SerialNumber;

public class LocomotiveExceptions {
	public static DomainException serialNumberAlreadyExists(SerialNumber serialNumber) {
		return error("ASSETS_LOCOMOTIVE_SERAL_NUMBER_ALREADY_EXISTS",
				"Locomotive with Serial Number '${serialNumber}' already exists .")
				.withParam("serialNumber", serialNumber.sn())
				.asException();
	}
}
