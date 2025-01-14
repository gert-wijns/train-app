package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm.personnel.EmployeeId.asEmployeeId;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import be.gert.trainapp.api.personnel.generated.AssignEmployeeRoleUseCaseApi;
import be.gert.trainapp.api.personnel.generated.model.AssignEmployeeRoleRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel.EmployeeRole;
import be.gert.trainapp.sm.personnel._event.EmployeeRoleAssigned;
import be.gert.trainapp.sm.personnel._repository.EmployeeJpaRepository;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class AssignEmployeeRoleUseCase implements AssignEmployeeRoleUseCaseApi {
	private final EmployeeJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public void execute(AssignEmployeeRoleRequest request) {
		EmployeeId employeeId = asEmployeeId(request.getEmployeeId());
		var employee = jpa.getById(employeeId);
		employee.assignRole(EmployeeRole.valueOf(request.getRole().getValue()));
		jpa.save(employee);
		eventPublisher.publishEvent(new EmployeeRoleAssigned(employeeId, employee.role()));
	}
}
