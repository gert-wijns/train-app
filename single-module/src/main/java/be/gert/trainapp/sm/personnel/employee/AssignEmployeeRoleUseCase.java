package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm.personnel.EmployeeId.asEmployeeId;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.personnel.generated.AssignEmployeeRoleUseCaseApi;
import be.gert.trainapp.api.personnel.generated.model.AssignEmployeeRoleRequest;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel._model.EmployeeRole;
import be.gert.trainapp.sm.personnel._repository.EmployeeJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AssignEmployeeRoleUseCase implements AssignEmployeeRoleUseCaseApi {
	private final EmployeeJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AssignEmployeeRoleRequest request) {
		EmployeeId employeeId = asEmployeeId(request.getEmployeeId());
		var employee = jpa.getById(employeeId);
		employee.assignRole(EmployeeRole.valueOf(request.getRole().getValue()));
		jpa.save(employee);
		return noContent().build();
	}
}
