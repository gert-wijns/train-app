package be.gert.trainapp.sm.personnel._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel._model.Employee;

@Repository
public interface EmployeeJpaRepository extends CrudRepository<Employee, EmployeeId> {
	static DomainException notFound(EmployeeId employeeId) {
		return error("PERSONNEL_EMPLOYEE_NOT_FOUND",
				"Employee not found for id '${id}'.")
				.withParam("id", employeeId.id())
				.asException();
	}

	default Employee getById(EmployeeId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
