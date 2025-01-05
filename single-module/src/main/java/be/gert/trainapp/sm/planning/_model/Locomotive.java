package be.gert.trainapp.sm.planning._model;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotivePowerType;
import be.gert.trainapp.sm.network.TrackGauge;

public record Locomotive(LocomotiveId id,
                         LocomotivePowerType powerType,
                         TrackGauge gauge,
                         boolean decommissioned) {
}
