package be.gert.trainapp.sm.planning._model;

import be.gert.trainapp.sm.assets.LocomotiveId;
import jakarta.persistence.Embeddable;

@Embeddable
public record TrainLocomotive(LocomotiveId id,
                              boolean electrified,
                              boolean decommissioned) {

}
