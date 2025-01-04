package be.gert.trainapp.sm.network._model;

import static java.math.RoundingMode.UP;

import java.math.BigDecimal;

import be.gert.trainapp.sm._shared.values.Speed;
import be.gert.trainapp.sm._shared.values.Speed.SpeedMeasurement;

public class SpeedDefaults {

	public static Speed kilometersPerHour(int speed) {
		return new Speed(new BigDecimal(speed).setScale(2, UP), SpeedMeasurement.KPH);
	}

}
