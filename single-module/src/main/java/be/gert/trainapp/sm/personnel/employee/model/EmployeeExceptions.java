package be.gert.trainapp.sm.personnel.employee.model;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.personnel.EmployeeId;

public class EmployeeExceptions {

	public static DomainException notFound(EmployeeId employeeId) {
		return error("PERSONNEL_EMPLOYEE_NOT_FOUND",
				"Employee not found for id '${id}'.")
				.withParam("id", employeeId.id())
				.asException();
	}

	public static DomainException alreadyExists(EmployeeId employeeId) {
		return error("PERSONNEL_EMPLOYEE_ALREADY_EXISTS",
				"Employee already exists for id '${id}'.")
				.withParam("id", employeeId.id())
				.asException();
	}
}
