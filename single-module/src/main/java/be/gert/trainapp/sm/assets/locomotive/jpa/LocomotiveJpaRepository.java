package be.gert.trainapp.sm.assets.locomotive.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.locomotive.model.Locomotive;

@Repository
public interface LocomotiveJpaRepository extends CrudRepository<Locomotive, LocomotiveId> {
}
