package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.assertEmployee;
import static be.gert.trainapp.sm.personnel.employee.NewEmployeeUseCase.alreadyExists;
import static be.gert.trainapp.sm.personnel._model.EmployeeRole.UNASSIGNED;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzales;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.personnel.generated.model.FullNameBody;
import be.gert.trainapp.api.personnel.generated.model.NewEmployeeRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.personnel._repository.EmployeeJpaRepository;

@ModuleTest
class NewEmployeeUseCaseTest {
	@Autowired
	EmployeeJpaRepository jpa;
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

		assertEmployee(jpa.getById(employeeChristineGonzalesId))
				.isEqualTo(employeeChristineGonzales()
					.toBuilder()
					.role(UNASSIGNED)
					.build());
	}

	@Test
	void throwsAlreadyExists() {
		jpa.save(employeeChristineGonzales());

		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(employeeChristineGonzalesId));
	}
}
