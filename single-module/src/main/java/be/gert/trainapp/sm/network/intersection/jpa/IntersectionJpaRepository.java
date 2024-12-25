package be.gert.trainapp.sm.network.intersection.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.nodes.NodeId;

import be.gert.trainapp.sm.network.intersection.model.Intersection;

@Repository
public interface IntersectionJpaRepository extends CrudRepository<Intersection, NodeId> {
}
