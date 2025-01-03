package be.gert.trainapp.sm.network.track.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.network.TrackId;
import be.gert.trainapp.sm.network.track.model.Track;

@Repository
public interface TrackJpaRepository extends CrudRepository<Track, TrackId> {
}
