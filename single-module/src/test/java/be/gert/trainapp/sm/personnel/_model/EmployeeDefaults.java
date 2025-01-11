package be.gert.trainapp.sm.personnel._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.personnel.EmployeeRole.TRAIN_ENGINEER;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm._shared.values.FullName;
import be.gert.trainapp.sm.personnel.EmployeeId;

public final class EmployeeDefaults {

	public static EmployeeId employeeChristineGonzalesId = new EmployeeId("christina");

	public static Employee employeeChristineGonzales() {
		return Employee.builder()
				.id(employeeChristineGonzalesId)
				.role(TRAIN_ENGINEER)
				.fullName(new FullName("Christina" , "Gonzales"))
				.build();
	}

}
