package be.gert.trainapp.sm.assets._events;

import static java.util.Objects.requireNonNull;

import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;

public record WagonAddedEvent(
		WagonId id,
		WagonModelId modelId,
		SerialNumber serialNumber)
		implements WagonEvent {
	public WagonAddedEvent {
		requireNonNull(id);
		requireNonNull(modelId);
		requireNonNull(serialNumber);
	}
}
