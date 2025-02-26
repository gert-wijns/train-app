package be.gert.trainapp.sm.assets._events;

import static be.gert.trainapp.sm._shared.requirements.StringRequirements.requireNotBlank;
import static java.util.Objects.requireNonNull;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.SerialNumber;

public record LocomotiveAddedEvent(
		LocomotiveId id,
		LocomotiveModelId modelId,
		SerialNumber serialNumber,
		String name) {
	public LocomotiveAddedEvent {
		requireNonNull(id);
		requireNonNull(modelId);
		requireNonNull(serialNumber);
		requireNotBlank(name);
	}
}
