package be.gert.trainapp.sm.personnel;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public record EmployeeId(String id) implements Serializable {
	public EmployeeId {
		requireNonNull(id);
	}

	public static EmployeeId asEmployeeId(String id) {
		return id == null ? null: new EmployeeId(id);
	}

	public static EmployeeId newEmployeeId() {
		return new EmployeeId(UUID.randomUUID().toString());
	}
}
