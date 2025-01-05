package be.gert.trainapp.sm.assets;

import jakarta.persistence.Embeddable;

@Embeddable
public record WagonId(String id) {
}
