package be.gert.trainapp.sm.assets.asset;

import static be.gert.trainapp.sm.assets._model.Asset.newAsset;
import static be.gert.trainapp.sm.assets._model.AssetType.LOCOMOTIVE;
import static be.gert.trainapp.sm.assets._model.AssetType.WAGON;

import org.springframework.context.event.EventListener;

import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets.AssetId;
import be.gert.trainapp.sm.assets._events.LocomotiveAddedEvent;
import be.gert.trainapp.sm.assets._events.WagonAddedEvent;
import be.gert.trainapp.sm.assets._repository.AssetJpaRepository;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class AddAssetUseCase {
	private final AssetJpaRepository assetJpaRepository;

	@EventListener
	void onLocomotiveAdded(LocomotiveAddedEvent event) {
		assetJpaRepository.save(newAsset(
				new AssetId(event.id().id()),
				LOCOMOTIVE,
				event.name(),
				event.modelId().id(),
				event.serialNumber()));
	}

	@EventListener
	void onWagonAdded(WagonAddedEvent event) {
		assetJpaRepository.save(newAsset(
				new AssetId(event.id().id()),
				WAGON,
				"",
				event.modelId().id(),
				event.serialNumber()));
	}
}
