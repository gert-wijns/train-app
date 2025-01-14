package be.gert.trainapp.sm.assets._repository;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.NOT_FOUND;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.KeyParam;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets._model.WagonModel;

@Repository
public interface WagonModelJpaRepository extends CrudRepository<WagonModel, WagonModelId> {
	static DomainException notFound(WagonModelId id) {
		return error("NOT_FOUND", "${entity} not found id '${id}'.")
				.withParam("entity", KeyParam.key("WAGON_MODEL"))
				.withParam("id", id.id())
				.asException(NOT_FOUND);
	}

	default WagonModel getById(WagonModelId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
