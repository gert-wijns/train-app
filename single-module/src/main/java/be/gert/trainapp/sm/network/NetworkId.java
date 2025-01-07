package be.gert.trainapp.sm.network;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record NetworkId(@Column(length = 36) String id) {
	public NetworkId {
		requireNonNull(id);
	}
}
