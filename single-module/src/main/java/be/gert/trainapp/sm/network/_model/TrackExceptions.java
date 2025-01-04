package be.gert.trainapp.sm.network._model;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.network.TrackId;

public class TrackExceptions {

	public static DomainException notFound(TrackId trackId) {
		return error("NETWORK_TRACK_NOT_FOUND",
				"Track not found for between '${fromId}' and '${toId}'.")
				.withParam("fromId", trackId.from().id())
				.withParam("toId", trackId.to().id())
				.asException();
	}

	public static DomainException alreadyExists(TrackId trackId) {
		return error("NETWORK_TRACK_ALREADY_EXISTS",
				"Track already exists between '${fromId}' and '${toId}'.")
				.withParam("fromId", trackId.from().id())
				.withParam("toId", trackId.to().id())
				.asException();
	}

}
