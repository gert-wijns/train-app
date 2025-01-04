package be.gert.trainapp.sm.network._repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.network.TrackId;
import be.gert.trainapp.sm.network._model.Track;

@Repository
public interface TrackJpaRepository extends CrudRepository<Track, TrackId> {
}
