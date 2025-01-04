package be.gert.trainapp.sm.assets._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets._model.Wagon;

@Repository
public interface WagonJpaRepository extends CrudRepository<Wagon, WagonId> {
	static DomainException notFound(WagonId id) {
		return error("ASSETS_WAGON_NOT_FOUND",
				"Wagon not found for id '${id}'.")
				.withParam("id", id.id())
				.asException();
	}

	default Wagon getById(WagonId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}

	boolean existsBySerialNumberIs(SerialNumber serialNumber);

}
