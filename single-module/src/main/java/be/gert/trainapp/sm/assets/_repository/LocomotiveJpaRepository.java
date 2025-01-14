package be.gert.trainapp.sm.assets._repository;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.NOT_FOUND;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.KeyParam;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets._model.Locomotive;

@Repository
public interface LocomotiveJpaRepository extends CrudRepository<Locomotive, LocomotiveId> {
	static DomainException notFound(LocomotiveId id) {
		return error("NOT_FOUND", "${entity} not found id '${id}'.")
				.withParam("entity", KeyParam.key("LOCOMOTIVE"))
				.withParam("id", id.id())
				.asException(NOT_FOUND);
	}

	default Locomotive getById(LocomotiveId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}

	boolean existsBySerialNumberIs(SerialNumber serialNumber);
}
