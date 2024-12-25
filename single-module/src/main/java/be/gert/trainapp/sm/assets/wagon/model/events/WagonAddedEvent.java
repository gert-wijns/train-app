package be.gert.trainapp.sm.assets.wagon.model.events;

import static java.util.Objects.requireNonNull;

import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;

public record WagonAddedEvent(
		WagonId id,
		WagonModelId modelId)
		implements WagonEvent {
	public WagonAddedEvent {
		requireNonNull(id);
		requireNonNull(modelId);
	}
}
