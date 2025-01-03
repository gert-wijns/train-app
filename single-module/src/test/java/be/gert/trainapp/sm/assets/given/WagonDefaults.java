package be.gert.trainapp.sm.assets.given;

import static be.gert.trainapp.sm.network.given.TrackDefaults.standardGauge;

import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets.wagon.model.Wagon;

public class WagonDefaults {
	public static final WagonId wagonId = new WagonId("1");
	public static final WagonModelId wagonModelXs = new WagonModelId("xs");
	public static final SerialNumber serialNumber = new SerialNumber("sn-1");

	public static Wagon testWagon() {
		return new Wagon(
				wagonId,
				wagonModelXs,
				serialNumber,
				standardGauge);
	}
}
