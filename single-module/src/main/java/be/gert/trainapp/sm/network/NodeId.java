package be.gert.trainapp.sm.network;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record NodeId(@Column(length = 36) String id) {
	public NodeId {
		requireNonNull(id);
	}
}
