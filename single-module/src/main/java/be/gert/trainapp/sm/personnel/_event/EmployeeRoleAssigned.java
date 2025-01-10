package be.gert.trainapp.sm.personnel._event;

import static java.util.Objects.requireNonNull;

import be.gert.trainapp.sm._shared.event.PublicEvent;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel.EmployeeRole;

@PublicEvent
public record EmployeeRoleAssigned(
		EmployeeId id,
		EmployeeRole role) {
	public EmployeeRoleAssigned {
		requireNonNull(id);
		requireNonNull(role);
	}
}
