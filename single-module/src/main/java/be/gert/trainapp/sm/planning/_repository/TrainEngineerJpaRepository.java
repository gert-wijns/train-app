package be.gert.trainapp.sm.planning._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning._model.TrainEngineer;

@Repository
public interface TrainEngineerJpaRepository extends CrudRepository<TrainEngineer, EmployeeId> {
	static DomainException notFound(EmployeeId id) {
		return error("PLANNING_TRAIN_ENGINEER_NOT_FOUND",
				"TrainEngineer not found id '${id}'.")
				.withParam("id", id.id())
				.asException();
	}

	default TrainEngineer getById(EmployeeId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
