package be.gert.trainapp.sm.assets._events;

import be.gert.trainapp.sm._shared.event.PublicEvent;
import be.gert.trainapp.sm.assets.WagonId;

@PublicEvent
public record WagonDecommissioned(WagonId id) {
}
