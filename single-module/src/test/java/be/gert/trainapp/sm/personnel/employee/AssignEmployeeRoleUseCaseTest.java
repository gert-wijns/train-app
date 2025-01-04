package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm.personnel.employee.model.EmployeeExceptions.notFound;
import static be.gert.trainapp.sm.personnel.employee.model.EmployeeRole.DIESEL_MECHANIC;
import static be.gert.trainapp.sm.personnel.given.EmployeeDefaults.employeeChristineGonzales;
import static be.gert.trainapp.sm.personnel.given.EmployeeDefaults.employeeChristineGonzalesId;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.personnel.generated.model.AssignEmployeeRoleRequest;
import be.gert.trainapp.api.personnel.generated.model.EmployeeRole;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;

@ModuleTest
public class AssignEmployeeRoleUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	AssignEmployeeRoleUseCase usecase;

	AssignEmployeeRoleRequest request = new AssignEmployeeRoleRequest()
			.employeeId(employeeChristineGonzalesId.id())
			.role(EmployeeRole.DIESEL_MECHANIC);

	@Test
	void success() {
		// given
		testEntities.save(employeeChristineGonzales());

		// when
		usecase.execute(request);

		// then
		testEntities.assertState(employeeChristineGonzales()
				.toBuilder()
				.role(DIESEL_MECHANIC)
				.build());
	}

	@Test
	void throwsNotFound() {
		// when
		assertThatThrownBy(() ->usecase.execute(request))
				.isEqualTo(notFound(employeeChristineGonzalesId));
	}
}
