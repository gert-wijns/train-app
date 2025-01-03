package be.gert.trainapp.sm.planning;


import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record RoutePlanId(@Column(length = 36) String id) {
	public RoutePlanId {
		requireNonNull(id);
	}
}
