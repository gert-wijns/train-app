package be.gert.trainapp.sm.assets._model;

import static be.gert.trainapp.sm.assets.WagonType.GONDOLA;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;

import be.gert.trainapp.sm.assets.WagonModelId;

public class WagonModelDefaults {

	public static final WagonModelId wagonModelXsId = new WagonModelId("xs");
	public static final String wagonModelXsName = "WagonModel-name";

	public static WagonModel wagonModelXs() {
		return WagonModel.builder()
				.id(wagonModelXsId)
				.name(wagonModelXsName)
				.gauge(standardGauge)
				.type(GONDOLA)
				.build();
	}

}
