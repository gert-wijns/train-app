package be.gert.trainapp.sm.assets.asset;

import static be.gert.trainapp.sm.assets.asset.model.Asset.newAsset;
import static be.gert.trainapp.sm.assets.asset.model.AssetType.LOCOMOTIVE;
import static be.gert.trainapp.sm.assets.asset.model.AssetType.WAGON;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import be.gert.trainapp.sm.assets.AssetId;
import be.gert.trainapp.sm.assets.asset.jpa.AssetJpaRepository;
import be.gert.trainapp.sm.assets.locomotive.model.events.LocomotiveAddedEvent;
import be.gert.trainapp.sm.assets.wagon.model.events.WagonAddedEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddAssetUseCase {
	private final AssetJpaRepository assetJpaRepository;

	@EventListener
	void onLocomotiveAdded(LocomotiveAddedEvent event) {
		assetJpaRepository.save(newAsset(new AssetId(event.id().id()), LOCOMOTIVE, event.name(), event.modelId().id()));
	}

	@EventListener
	void onWagonAdded(WagonAddedEvent event) {
		assetJpaRepository.save(newAsset(new AssetId(event.id().id()), WAGON, "", event.modelId().id()));
	}
}
