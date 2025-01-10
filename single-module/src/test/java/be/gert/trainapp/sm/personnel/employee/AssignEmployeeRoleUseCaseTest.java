package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.assertEmployee;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzales;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.personnel.EmployeeRole.DIESEL_MECHANIC;
import static be.gert.trainapp.sm.personnel._repository.EmployeeJpaRepository.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.personnel.generated.model.AssignEmployeeRoleRequest;
import be.gert.trainapp.api.personnel.generated.model.EmployeeRole;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.personnel._repository.EmployeeJpaRepository;

@ModuleTest
class AssignEmployeeRoleUseCaseTest {
	@Autowired
	EmployeeJpaRepository jpa;
	@Autowired
	AssignEmployeeRoleUseCase usecase;

	AssignEmployeeRoleRequest request = new AssignEmployeeRoleRequest()
			.employeeId(employeeChristineGonzalesId.id())
			.role(EmployeeRole.DIESEL_MECHANIC);

	@Test
	void success() {
		// given
		jpa.save(employeeChristineGonzales());

		// when
		usecase.execute(request);

		// then
		assertEmployee(jpa.getById(employeeChristineGonzalesId))
				.isEqualTo(employeeChristineGonzales()
					.toBuilder()
					.role(DIESEL_MECHANIC)
					.build());
	}

	@Test
	void throwsNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(employeeChristineGonzalesId));
	}
}
