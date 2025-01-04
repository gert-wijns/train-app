package be.gert.trainapp.sm.network._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network._model.Node;

@Repository
public interface NodeJpaRepository extends CrudRepository<Node, NodeId> {
	static DomainException notFound(NodeId nodeId) {
		return error("NETWORK_NODE_NOT_FOUND",
				"Node not found for id '${id}'.")
				.withParam("id", nodeId.id())
				.asException();
	}

	default Node getById(NodeId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
