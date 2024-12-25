package be.gert.trainapp.sm.network;

import jakarta.persistence.Embeddable;

@Embeddable
public record NodeId(String id) {
}
