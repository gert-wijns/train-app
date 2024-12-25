package be.gert.trainapp.sm.personnel.given;

import static be.gert.trainapp.sm.personnel.employee.model.EmployeeRole.TRAIN_ENGINEER;

import be.gert.trainapp.sm._shared.values.FullName;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel.employee.model.Employee;

public final class EmployeeDefaults {

	public static EmployeeId employeeChristineGonzalesId = new EmployeeId("christina");

	public static Employee employeeChristineGonzales() {
		return new Employee()
				.withId(employeeChristineGonzalesId)
				.withRole(TRAIN_ENGINEER)
				.withFullName(new FullName("Christina" , "Gonzales"));
	}
}
