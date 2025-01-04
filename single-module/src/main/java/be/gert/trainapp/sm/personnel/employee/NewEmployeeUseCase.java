package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm.personnel.EmployeeId.asEmployeeId;
import static be.gert.trainapp.sm.personnel._mapper.FullNameMapper.toFullName;
import static be.gert.trainapp.sm.personnel._model.Employee.newEmployee;
import static be.gert.trainapp.sm.personnel._model.EmployeeExceptions.alreadyExists;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.personnel.generated.NewEmployeeUseCaseApi;
import be.gert.trainapp.api.personnel.generated.model.NewEmployeeRequest;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel._repository.EmployeeJpaRepository;
import be.gert.trainapp.sm.personnel._events.EmployeeHired;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class NewEmployeeUseCase implements NewEmployeeUseCaseApi {
	private final EmployeeJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(NewEmployeeRequest request) {
		EmployeeId employeeId = asEmployeeId(request.getEmployeeId());
		if (jpa.findById(employeeId).isPresent()) {
			throw alreadyExists(employeeId);
		}
		var employee = jpa.save(newEmployee(employeeId)
				.fullName(toFullName(request.getFullName())));
		eventPublisher.publishEvent(new EmployeeHired(employee.id()));
		return noContent().build();
	}

}
