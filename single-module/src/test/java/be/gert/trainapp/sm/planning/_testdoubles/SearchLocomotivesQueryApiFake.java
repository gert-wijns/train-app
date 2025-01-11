package be.gert.trainapp.sm.planning._testdoubles;

import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static be.gert.trainapp.sm.planning._model.LocomotiveDefaults.locomotiveOrientExpressId;
import static be.gert.trainapp.sm.planning._model.TrainLocomotiveDefaults.orientExpressLocomotive;

import java.util.List;

import org.springframework.http.ResponseEntity;

import be.gert.trainapp.api.assets.generated.SearchLocomotivesQueryApi;
import be.gert.trainapp.api.assets.generated.model.LocomotiveModelResponse;
import be.gert.trainapp.api.assets.generated.model.LocomotivePowerType;
import be.gert.trainapp.api.assets.generated.model.SearchLocomotivesQueryResponseItem;

public class SearchLocomotivesQueryApiFake implements SearchLocomotivesQueryApi {
	@Override
	public ResponseEntity<List<SearchLocomotivesQueryResponseItem>> query(List<String> locomotiveId) {
		return ResponseEntity.ok(List.of(new SearchLocomotivesQueryResponseItem()
				.id(locomotiveOrientExpressId.id())
				.name("Locomotive-orientExpress")
				.decommissioned(false)
				.serialNumber(orientExpressLocomotive.serialNumber().sn())
				.model(new LocomotiveModelResponse()
						.id("Locomotive-orientExpress-ModelId")
						.gauge(standardGauge.type())
						.powerType(LocomotivePowerType.ELECTRIC))));
	}
}
