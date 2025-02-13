package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.planning._model.TrainEngineerDefaults.trainEngineer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AopTestUtils;

import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.personnel._event.EmployeeRoleAssigned;
import be.gert.trainapp.sm.personnel.EmployeeRole;
import be.gert.trainapp.sm.planning._repository.TrainEngineerJpaRepository;

@ModuleCoreTest
class OnEmployeeRoleAssignedUseCaseTest {
	@Autowired
	TrainEngineerJpaRepository jpa;
	@Autowired
	OnEmployeeRoleAssignedUseCase usecase;

	@BeforeEach
	void setup() {
		// strip Transactional.NEW by ApplicationModuleListener for test...
		usecase = AopTestUtils.getTargetObject(usecase);
	}

	@Test
	void createWhenTrainEngineerRoleAssigned() {
		usecase.onEmployeeRoleAssigned(new EmployeeRoleAssigned(
				employeeChristineGonzalesId,
				EmployeeRole.TRAIN_ENGINEER));
	}

	@Test
	void activateWhenTrainEngineerRoleAssigned() {
		jpa.save(trainEngineer().toBuilder().active(false).build());

		usecase.onEmployeeRoleAssigned(new EmployeeRoleAssigned(
				employeeChristineGonzalesId,
				EmployeeRole.TRAIN_ENGINEER));
	}

	@ParameterizedTest
	@EnumSource(value = EmployeeRole.class, names = "TRAIN_ENGINEER", mode = Mode.EXCLUDE)
	void deactivateWhenOtherRoleAssigned(EmployeeRole otherRole) {
		jpa.save(trainEngineer());

		usecase.onEmployeeRoleAssigned(new EmployeeRoleAssigned(
				employeeChristineGonzalesId,
				otherRole));
	}
}
