package be.gert.trainapp.sm.planning._testdoubles;

import static be.gert.trainapp.sm.planning._model.WagonDefaults.orientExpressFirstCoach;
import static be.gert.trainapp.sm.planning._model.WagonDefaults.wagonModelTypeId;

import java.util.List;

import org.springframework.http.ResponseEntity;

import be.gert.trainapp.api.assets.generated.SearchWagonsQueryApi;
import be.gert.trainapp.api.assets.generated.model.SearchWagonsQueryResponseItem;
import be.gert.trainapp.api.assets.generated.model.WagonModelResponse;
import be.gert.trainapp.sm.planning._model.Wagon;

public class SearchWagonsQueryApiFake implements SearchWagonsQueryApi {
	@Override
	public ResponseEntity<List<SearchWagonsQueryResponseItem>> query(List<String> wagonId) {
		Wagon wagon = orientExpressFirstCoach();
		return ResponseEntity.ok(List.of(new SearchWagonsQueryResponseItem()
				.id(wagon.id().id())
				.serialNumber(wagon.serialNumber().sn())
				.decommissioned(wagon.decommissioned())
				.model(new WagonModelResponse()
						.id(wagonModelTypeId.id())
						.gauge(wagon.gauge().type()))));
	}
}
