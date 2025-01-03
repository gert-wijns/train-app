package be.gert.trainapp.sm.network;

import static be.gert.trainapp.sm.network.Speed.SpeedMeasurement.KPH;
import static be.gert.trainapp.sm.network.Speed.SpeedMeasurement.MPH;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record Speed(BigDecimal speed,
                    @Enumerated(EnumType.STRING)
                    SpeedMeasurement measurement) {
	private static final BigDecimal FACTOR = new BigDecimal("0.620");
	public enum SpeedMeasurement {
		KPH, MPH
	}

	BigDecimal kiloMetersPerHour() {
		return measurement == KPH ? speed: speed.divide(FACTOR, HALF_UP);
	}

	BigDecimal milesPerHour() {
		return measurement == MPH ? speed: speed.multiply(FACTOR);
	}
}
