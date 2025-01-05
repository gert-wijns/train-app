package be.gert.trainapp.sm.planning._testdoubles;

import static be.gert.trainapp.sm.planning._model.TrainDefaults.wagonModelTypeId;

import java.util.List;

import org.springframework.http.ResponseEntity;

import be.gert.trainapp.api.assets.generated.SearchWagonsQueryApi;
import be.gert.trainapp.api.assets.generated.model.SearchWagonsQueryResponseItem;

public class SearchWagonsQueryApiFake implements SearchWagonsQueryApi {
	@Override
	public ResponseEntity<List<SearchWagonsQueryResponseItem>> query(List<String> wagonId) {
		return ResponseEntity.ok(List.of(new SearchWagonsQueryResponseItem()
				.modelTypeId(wagonModelTypeId.id())));
	}
}
