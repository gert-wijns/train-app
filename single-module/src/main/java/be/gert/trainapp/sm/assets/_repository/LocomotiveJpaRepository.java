package be.gert.trainapp.sm.assets._repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets._model.Locomotive;

@Repository
public interface LocomotiveJpaRepository extends CrudRepository<Locomotive, LocomotiveId> {
	boolean existsBySerialNumberIs(SerialNumber serialNumber);
}
