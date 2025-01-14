package be.gert.trainapp.sm.network._repository;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.NOT_FOUND;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.KeyParam;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network._model.Node;

@Repository
public interface NodeJpaRepository extends CrudRepository<Node, NodeId> {
	static DomainException notFound(NodeId id) {
		return error("NOT_FOUND", "${entity} not found id '${id}'.")
				.withParam("entity", KeyParam.key("NODE"))
				.withParam("id", id.id())
				.asException(NOT_FOUND);
	}

	default Node getById(NodeId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
