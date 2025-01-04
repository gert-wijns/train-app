package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm.personnel.employee.model.EmployeeExceptions.alreadyExists;
import static be.gert.trainapp.sm.personnel.employee.model.EmployeeRole.UNASSIGNED;
import static be.gert.trainapp.sm.personnel.given.EmployeeDefaults.employeeChristineGonzales;
import static be.gert.trainapp.sm.personnel.given.EmployeeDefaults.employeeChristineGonzalesId;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.personnel.generated.model.FullNameBody;
import be.gert.trainapp.api.personnel.generated.model.NewEmployeeRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;

@ModuleTest
class NewEmployeeUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	NewEmployeeUseCase usecase;

	NewEmployeeRequest request = new NewEmployeeRequest()
			.employeeId(employeeChristineGonzalesId.id())
			.fullName(new FullNameBody()
					.firstName(employeeChristineGonzales().fullName().firstName())
					.lastName(employeeChristineGonzales().fullName().lastName()));

	@Test
	void success() {
		usecase.execute(request);

		testEntities.assertState(employeeChristineGonzales()
				.toBuilder()
				.role(UNASSIGNED)
				.build());
	}

	@Test
	void throwsAlreadyExists() {
		testEntities.save(employeeChristineGonzales());

		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(employeeChristineGonzalesId));
	}
}
