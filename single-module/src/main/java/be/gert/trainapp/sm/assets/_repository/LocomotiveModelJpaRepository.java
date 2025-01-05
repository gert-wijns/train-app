package be.gert.trainapp.sm.assets._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets._model.LocomotiveModel;

@Repository
public interface LocomotiveModelJpaRepository extends CrudRepository<LocomotiveModel, LocomotiveModelId> {
	static DomainException notFound(LocomotiveModelId id) {
		return error("ASSETS_LOCOMOTIVE_MODEL_NOT_FOUND",
				"LocomotiveModel not found for id '${id}'.")
				.withParam("id", id.id())
				.asException();
	}

	default LocomotiveModel getById(LocomotiveModelId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
