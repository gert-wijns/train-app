package be.gert.trainapp.sm.planning._testdoubles;

import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.wagonModelTypeId;

import java.util.List;

import org.springframework.http.ResponseEntity;

import be.gert.trainapp.api.assets.generated.SearchWagonsQueryApi;
import be.gert.trainapp.api.assets.generated.model.SearchWagonsQueryResponseItem;
import be.gert.trainapp.api.assets.generated.model.WagonModelResponse;
import be.gert.trainapp.sm.planning._model.TrainDefaults;

public class SearchWagonsQueryApiFake implements SearchWagonsQueryApi {
	@Override
	public ResponseEntity<List<SearchWagonsQueryResponseItem>> query(List<String> wagonId) {
		return ResponseEntity.ok(List.of(new SearchWagonsQueryResponseItem()
				.id(TrainDefaults.trainOrientExpressFirstCoachId.id())
				.serialNumber("wagon-sn")
				.decommissioned(false)
				.model(new WagonModelResponse()
						.id(wagonModelTypeId.id())
						.gauge(standardGauge.type()))));
	}
}
