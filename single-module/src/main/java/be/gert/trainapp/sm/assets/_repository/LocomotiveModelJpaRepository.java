package be.gert.trainapp.sm.assets._repository;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.NOT_FOUND;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.KeyParam;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets._model.LocomotiveModel;

@Repository
public interface LocomotiveModelJpaRepository extends CrudRepository<LocomotiveModel, LocomotiveModelId> {
	static DomainException notFound(LocomotiveModelId id) {
		return error("NOT_FOUND", "${entity} not found id '${id}'.")
				.withParam("entity", KeyParam.key("LOCOMOTIVE_MODEL"))
				.withParam("id", id.id())
				.asException(NOT_FOUND);
	}

	default LocomotiveModel getById(LocomotiveModelId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
