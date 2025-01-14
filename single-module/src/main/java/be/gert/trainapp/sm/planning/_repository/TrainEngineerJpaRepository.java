package be.gert.trainapp.sm.planning._repository;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.NOT_FOUND;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.KeyParam;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning._model.TrainEngineer;

@Repository
public interface TrainEngineerJpaRepository extends CrudRepository<TrainEngineer, EmployeeId> {
	static DomainException notFound(EmployeeId id) {
		return error("NOT_FOUND", "${entity} not found id '${id}'.")
				.withParam("entity", KeyParam.key("TRAIN_ENGINEER"))
				.withParam("id", id.id())
				.asException(NOT_FOUND);
	}

	default TrainEngineer getById(EmployeeId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
