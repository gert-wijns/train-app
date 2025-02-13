package be.gert.trainapp.sm.planning._adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import be.gert.trainapp.api.assets.generated.SearchWagonsQueryApi;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.planning._model.Wagon;
import be.gert.trainapp.sm.planning._port.SearchWagon;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SearchWagonUsingAssetsApi implements SearchWagon {
	private final SearchWagonsQueryApi searchWagonQueryApi;

	public Wagon getById(WagonId id) {
		var response = searchWagonQueryApi.query(List.of(id.id())).getFirst();
		return new Wagon(
				id,
				new SerialNumber(response.getSerialNumber()),
				new TrackGauge(response.getModel().getGauge()),
				response.getDecommissioned());
	}
}
