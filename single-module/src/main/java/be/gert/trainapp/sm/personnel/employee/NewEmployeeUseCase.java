package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.CONFLICT;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.personnel.EmployeeId.asEmployeeId;
import static be.gert.trainapp.sm.personnel._mapper.FullNameMapper.toFullName;
import static be.gert.trainapp.sm.personnel._model.Employee.newEmployee;

import org.springframework.security.access.prepost.PreAuthorize;

import be.gert.trainapp.api.personnel.generated.NewEmployeeUseCaseApi;
import be.gert.trainapp.api.personnel.generated.model.NewEmployeeRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel._repository.EmployeeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class NewEmployeeUseCase implements NewEmployeeUseCaseApi {
	public static DomainException alreadyExists(EmployeeId employeeId) {
		return error("PERSONNEL_EMPLOYEE_ALREADY_EXISTS",
				"Employee already exists for id '${id}'.")
				.withParam("id", employeeId.id())
				.asException(CONFLICT);
	}

	private final EmployeeJpaRepository jpa;

	@Override
	@Transactional
	@PreAuthorize("hasAnyRole(@PersonnelAuthRoles.ADMIN, @PersonnelAuthRoles.HR)")
	public void execute(NewEmployeeRequest request) {
		EmployeeId employeeId = asEmployeeId(request.getEmployeeId());
		if (jpa.findById(employeeId).isPresent()) {
			throw alreadyExists(employeeId);
		}
		jpa.save(newEmployee(employeeId)
				.fullName(toFullName(request.getFullName())));
	}

}
