package be.gert.trainapp.sm.personnel.employee.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel.employee.model.Employee;

@Repository
public interface EmployeeJpaRepository extends CrudRepository<Employee, EmployeeId> {
}
