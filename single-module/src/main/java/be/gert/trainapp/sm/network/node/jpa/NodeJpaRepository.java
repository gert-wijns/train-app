package be.gert.trainapp.sm.network.node.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network.node.model.Node;

@Repository
public interface NodeJpaRepository extends CrudRepository<Node, NodeId> {
}
