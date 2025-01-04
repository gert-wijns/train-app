package be.gert.trainapp.sm.assets._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.AssetId;
import be.gert.trainapp.sm.assets._model.Asset;

@Repository
public interface AssetJpaRepository extends CrudRepository<Asset, AssetId> {
	static DomainException notFound(AssetId id) {
		return error("ASSETS_ASSET_NOT_FOUND",
				"Asset not found for id '${id}'.")
				.withParam("id", id.id())
				.asException();
	}

	default Asset getById(AssetId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
