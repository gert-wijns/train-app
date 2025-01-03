package be.gert.trainapp.sm.network.node.model;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.network.NodeId;

public class NodeExceptions {
	public static DomainException notFound(NodeId nodeId) {
		return error("NETWORK_NODE_NOT_FOUND",
				"Node not found for id '${id}'.")
				.withParam("id", nodeId.id())
				.asException();
	}

	public static DomainException alreadyExists(NodeId nodeId) {
		return error("NETWORK_NODE_ALREADY_EXISTS",
				"Node already exists for id '${id}'.")
				.withParam("id", nodeId.id())
				.asException();
	}

}
