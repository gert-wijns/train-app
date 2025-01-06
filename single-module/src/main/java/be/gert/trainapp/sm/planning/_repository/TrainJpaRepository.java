package be.gert.trainapp.sm.planning._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._model.Train;

@Repository
public interface TrainJpaRepository extends CrudRepository<Train, TrainId> {
	static DomainException notFound(TrainId trainId) {
		return error("PLANNING_TRAIN_NOT_FOUND",
				"Train not found id '${id}'.")
				.withParam("id", trainId.id())
				.asException();
	}

	default Train getById(TrainId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}

	@Query("""
			select t from Train t
			where exists (
			    select w.id
			    from TrainWagon w
			    where w.train.id = t.id
			      and w.id = :wagonId)""")
	Optional<Train> findByWagonId(WagonId wagonId);

	Optional<Train> findByLocomotiveId(LocomotiveId locomotiveId);
}
