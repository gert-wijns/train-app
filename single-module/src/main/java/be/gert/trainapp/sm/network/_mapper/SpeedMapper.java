package be.gert.trainapp.sm.network._mapper;

import be.gert.trainapp.api.network.generated.model.SpeedBody;
import be.gert.trainapp.api.network.generated.model.SpeedBody.MeasurementEnum;
import be.gert.trainapp.sm.network.Speed;
import be.gert.trainapp.sm.network.Speed.SpeedMeasurement;

public class SpeedMapper {
	public static SpeedBody toSpeedBody(Speed speed) {
		return speed == null ? null: new SpeedBody()
				.speed(speed.speed())
				.measurement(MeasurementEnum.fromValue(speed.measurement().name()));
	}
	public static Speed toSpeed(SpeedBody speed) {
		return speed == null ? null: new Speed(
				speed.getSpeed(),
				SpeedMeasurement.valueOf(speed.getMeasurement().getValue()));
	}
}
