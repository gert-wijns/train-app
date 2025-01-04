package be.gert.trainapp.sm.assets._repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.assets.AssetId;
import be.gert.trainapp.sm.assets._model.Asset;

@Repository
public interface AssetJpaRepository extends CrudRepository<Asset, AssetId> {
}
