package be.gert.trainapp.sm.planning;

import jakarta.persistence.Embeddable;

@Embeddable
public record TrainId(String id) {
}
