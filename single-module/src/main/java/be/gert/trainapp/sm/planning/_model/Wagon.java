package be.gert.trainapp.sm.planning._model;

import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.network.TrackGauge;

public record Wagon(WagonId id,
                    TrackGauge gauge,
                    boolean decommissioned) {
}
