package be.gert.trainapp.sm.personnel._events;

import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel._model.EmployeeRole;

public record EmployeeRoleAssigned(EmployeeId id, EmployeeRole role) implements EmployeeEvent {
}
