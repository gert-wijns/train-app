package be.gert.trainapp.sm.planning.train.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning.train.model.Train;

@Repository
public interface TrainJpaRepository extends CrudRepository<Train, TrainId> {
}
