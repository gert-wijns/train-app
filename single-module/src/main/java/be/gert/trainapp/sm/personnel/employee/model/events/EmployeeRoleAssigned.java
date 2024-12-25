package be.gert.trainapp.sm.personnel.employee.model.events;

import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel.employee.model.EmployeeRole;

public record EmployeeRoleAssigned(EmployeeId id, EmployeeRole role) implements EmployeeEvent {
}
