package be.gert.trainapp.sm.assets.wagon.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.wagon.model.Wagon;

@Repository
public interface WagonJpaRepository extends CrudRepository<Wagon, WagonId> {
	boolean existsBySerialNumberIs(SerialNumber serialNumber);
}
