package be.gert.trainapp.sm.personnel;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record EmployeeId(@Column(length = 36) String id) {
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
