package be.gert.trainapp.sm.assets._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets._model.WagonModel;

@Repository
public interface WagonModelJpaRepository extends CrudRepository<WagonModel, WagonModelId> {
	static DomainException notFound(WagonModelId id) {
		return error("ASSETS_WAGON_MODEL_NOT_FOUND",
				"WagonModel not found for id '${id}'.")
				.withParam("id", id.id())
				.asException();
	}

	default WagonModel getById(WagonModelId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
