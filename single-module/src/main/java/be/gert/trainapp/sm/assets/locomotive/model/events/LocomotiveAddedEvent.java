package be.gert.trainapp.sm.assets.locomotive.model.events;

import static be.gert.trainapp.sm._shared.requirements.StringRequirements.requireNotBlank;
import static java.util.Objects.requireNonNull;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;

public record LocomotiveAddedEvent(
		LocomotiveId id,
		LocomotiveModelId modelId,
		String name)
		implements LocomotiveEvent {
	public LocomotiveAddedEvent {
		requireNonNull(id);
		requireNonNull(modelId);
		requireNotBlank(name);
	}
}
