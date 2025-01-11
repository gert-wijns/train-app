package be.gert.trainapp.sm.personnel._model;

import static be.gert.trainapp.sm.personnel.EmployeeRole.TRAIN_ENGINEER;

import be.gert.trainapp.sm._shared.values.FullName;
import be.gert.trainapp.sm.personnel.EmployeeId;

public final class EmployeeDefaults {

	public static EmployeeId employeeChristineGonzalesId = new EmployeeId("christina");

	public static Employee employeeChristineGonzales() {
		return new Employee(employeeChristineGonzalesId,
				new FullName("Christina" , "Gonzales"),
				TRAIN_ENGINEER);
	}

}
