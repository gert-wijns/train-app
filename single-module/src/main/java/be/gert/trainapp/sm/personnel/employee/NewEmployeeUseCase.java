package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.personnel.EmployeeId.asEmployeeId;
import static be.gert.trainapp.sm.personnel._mapper.FullNameMapper.toFullName;
import static be.gert.trainapp.sm.personnel._model.Employee.newEmployee;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.personnel.generated.NewEmployeeUseCaseApi;
import be.gert.trainapp.api.personnel.generated.model.NewEmployeeRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel._repository.EmployeeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class NewEmployeeUseCase implements NewEmployeeUseCaseApi {
	public static DomainException alreadyExists(EmployeeId employeeId) {
		return error("PERSONNEL_EMPLOYEE_ALREADY_EXISTS",
				"Employee already exists for id '${id}'.")
				.withParam("id", employeeId.id())
				.asException();
	}

	private final EmployeeJpaRepository jpa;

	@Override
	@Transactional
	//@PreAuthorize("hasAnyRole('admin', 'hr')")
	public ResponseEntity<Void> execute(NewEmployeeRequest request) {
		EmployeeId employeeId = asEmployeeId(request.getEmployeeId());
		if (jpa.findById(employeeId).isPresent()) {
			throw alreadyExists(employeeId);
		}
		jpa.save(newEmployee(employeeId)
				.fullName(toFullName(request.getFullName())));
		return noContent().build();
	}

}
