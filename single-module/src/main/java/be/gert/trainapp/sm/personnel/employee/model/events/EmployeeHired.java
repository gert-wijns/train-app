package be.gert.trainapp.sm.personnel.employee.model.events;

import be.gert.trainapp.sm.personnel.EmployeeId;

public record EmployeeHired(EmployeeId id) implements EmployeeEvent {
}
