package be.gert.trainapp.sm._localhost;

import static be.gert.trainapp.api.personnel.generated.model.EmployeeRole.TRAIN_CONDUCTOR;
import static be.gert.trainapp.api.personnel.generated.model.EmployeeRole.TRAIN_ENGINEER;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.math.BigInteger;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.personnel.generated.model.AssignEmployeeRoleRequest;
import be.gert.trainapp.api.personnel.generated.model.FullNameBody;
import be.gert.trainapp.api.personnel.generated.model.NewEmployeeRequest;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel.employee.AssignEmployeeRoleUseCase;
import be.gert.trainapp.sm.personnel.employee.NewEmployeeUseCase;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class EmployeeDataLoader {
	private final NewEmployeeUseCase newEmployeeUseCase;
	private final AssignEmployeeRoleUseCase assignEmployeeRoleUseCase;

	static final EmployeeId employeeJohnId = new EmployeeId(toHex("John"));
	static final EmployeeId employeeCaseyId = new EmployeeId(toHex("Casey"));
	static final EmployeeId employeeChristinaId = new EmployeeId(toHex("Christina"));
	static final EmployeeId employeeHannahId = new EmployeeId(toHex("Hannah"));

	private static String toHex(String arg) {
		return String.format("%x", new BigInteger(1, arg.getBytes(UTF_8)));
	}

	void loadEmployees() {
		newEmployeeUseCase.execute(newEmployeeChristinaRequest());
		newEmployeeUseCase.execute(newEmployeeHannahRequest());
		newEmployeeUseCase.execute(newEmployeeCaseyRequest());
		newEmployeeUseCase.execute(newEmployeeJohnRequest());

		assignEmployeeRoleUseCase.execute(new AssignEmployeeRoleRequest()
				.employeeId(employeeChristinaId.id())
				.role(TRAIN_ENGINEER));
		assignEmployeeRoleUseCase.execute(new AssignEmployeeRoleRequest()
				.employeeId(employeeHannahId.id())
				.role(TRAIN_ENGINEER));
		assignEmployeeRoleUseCase.execute(new AssignEmployeeRoleRequest()
				.employeeId(employeeCaseyId.id())
				.role(TRAIN_CONDUCTOR));
		assignEmployeeRoleUseCase.execute(new AssignEmployeeRoleRequest()
				.employeeId(employeeJohnId.id())
				.role(TRAIN_CONDUCTOR));
	}

	static NewEmployeeRequest newEmployeeJohnRequest() {
		return new NewEmployeeRequest()
				.employeeId(employeeJohnId.id())
				.fullName(new FullNameBody()
						.firstName("John")
						.lastName("Armstrong"));
	}

	static NewEmployeeRequest newEmployeeCaseyRequest() {
		return new NewEmployeeRequest()
				.employeeId(employeeCaseyId.id())
				.fullName(new FullNameBody()
						.firstName("Casey")
						.lastName("Jones"));
	}

	static NewEmployeeRequest newEmployeeChristinaRequest() {
		return new NewEmployeeRequest()
				.employeeId(employeeChristinaId.id())
				.fullName(new FullNameBody()
						.firstName("Christina")
						.lastName("Gonzales"));
	}

	static NewEmployeeRequest newEmployeeHannahRequest() {
		return new NewEmployeeRequest()
				.employeeId(employeeHannahId.id())
				.fullName(new FullNameBody()
						.firstName("Hannah")
						.lastName("Dadds"));
	}

}
