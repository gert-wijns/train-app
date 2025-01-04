package be.gert.trainapp.sm.personnel._events;

import be.gert.trainapp.sm.personnel.EmployeeId;

public record EmployeeHired(EmployeeId id) implements EmployeeEvent {
}
