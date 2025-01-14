package be.gert.trainapp.sm.planning._adapter;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.springframework.stereotype.Component;

import be.gert.trainapp.api.assets.generated.SearchLocomotivesQueryApi;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotivePowerType;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.planning._model.Locomotive;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SearchLocomotive {
	private final SearchLocomotivesQueryApi searchLocomotivesQueryApi;

	public Locomotive getById(LocomotiveId id) {
		var response = requireNonNull(searchLocomotivesQueryApi.query(List.of(id.id()))).getFirst();
		return new Locomotive(
				id,
				new SerialNumber(response.getSerialNumber()),
				LocomotivePowerType.valueOf(response.getModel().getPowerType().getValue()),
				new TrackGauge(response.getModel().getGauge()),
				response.getDecommissioned());
	}
}
