package be.gert.trainapp.sm.assets._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXsId;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;

public class WagonDefaults {
	public static final WagonId wagonId = new WagonId("1");
	public static final SerialNumber serialNumber = new SerialNumber("sn-1");

	public static Wagon testWagon() {
		return new Wagon(
				wagonId,
				wagonModelXsId,
				serialNumber,
				false);
	}

}
