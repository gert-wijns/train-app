package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm._config.UserDetailsServiceFake.withRoles;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.assertEmployee;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzales;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.personnel._model.EmployeeRole.UNASSIGNED;
import static be.gert.trainapp.sm.personnel.employee.NewEmployeeUseCase.alreadyExists;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;

import be.gert.trainapp.api.personnel.generated.model.FullNameBody;
import be.gert.trainapp.api.personnel.generated.model.NewEmployeeRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.personnel._model.PersonnelAuthRoles;
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

	@ParameterizedTest
	@ValueSource(strings = {PersonnelAuthRoles.ADMIN, PersonnelAuthRoles.HR})
	void success(String role) {
		withRoles(role);
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

		withRoles(PersonnelAuthRoles.ADMIN);
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(employeeChristineGonzalesId));
	}

	@Test
	void deniesWithoutRoles() {
		assertThatThrownBy(() -> usecase.execute(request))
				.isInstanceOf(AuthorizationDeniedException.class);
	}


}
