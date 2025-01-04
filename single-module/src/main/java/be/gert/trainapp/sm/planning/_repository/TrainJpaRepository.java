package be.gert.trainapp.sm.planning._repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._model.Train;

@Repository
public interface TrainJpaRepository extends CrudRepository<Train, TrainId> {
}
