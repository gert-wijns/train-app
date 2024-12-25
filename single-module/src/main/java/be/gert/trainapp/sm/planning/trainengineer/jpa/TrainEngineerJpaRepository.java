package be.gert.trainapp.sm.planning.trainengineer.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning.trainengineer.model.TrainEngineer;

@Repository
public interface TrainEngineerJpaRepository extends CrudRepository<TrainEngineer, EmployeeId> {
}
