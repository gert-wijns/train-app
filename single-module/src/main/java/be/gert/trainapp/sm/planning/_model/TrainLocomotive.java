package be.gert.trainapp.sm.planning._model;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.SerialNumber;
import jakarta.persistence.Embeddable;
import lombok.With;

@Embeddable
@With
public record TrainLocomotive(LocomotiveId id,
							  SerialNumber serialNumber,
                              boolean electrified,
                              boolean decommissioned) {
}
