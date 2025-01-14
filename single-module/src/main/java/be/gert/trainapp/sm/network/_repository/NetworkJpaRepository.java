package be.gert.trainapp.sm.network._repository;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.NOT_FOUND;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.KeyParam;
import be.gert.trainapp.sm.network.NetworkId;
import be.gert.trainapp.sm.network._model.Network;

@Repository
public interface NetworkJpaRepository extends CrudRepository<Network, NetworkId> {
	static DomainException notFound(NetworkId id) {
		return error("NOT_FOUND", "${entity} not found id '${id}'.")
				.withParam("entity", KeyParam.key("NETWORK"))
				.withParam("id", id.id())
				.asException(NOT_FOUND);
	}

	default Network getById(NetworkId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
