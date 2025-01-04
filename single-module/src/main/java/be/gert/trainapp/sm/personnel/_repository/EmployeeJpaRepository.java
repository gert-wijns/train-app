package be.gert.trainapp.sm.personnel._repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel._model.Employee;

@Repository
public interface EmployeeJpaRepository extends CrudRepository<Employee, EmployeeId> {
}
