package be.gert.trainapp.sm.planning._adapter;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.springframework.stereotype.Component;

import be.gert.trainapp.api.assets.generated.SearchWagonsQueryApi;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.planning._model.Wagon;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SearchWagon {
	private final SearchWagonsQueryApi searchWagonQueryApi;

	public Wagon getById(WagonId id) {
		var response = requireNonNull(searchWagonQueryApi.query(List.of(id.id())).getBody()).getFirst();
		return new Wagon(
				id,
				new TrackGauge(response.getModel().getGauge()),
				response.getDecommissioned());
	}
}
