package be.gert.trainapp.sm._shared.values;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;
import static java.math.RoundingMode.UP;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record GeoPosition(
		@Column(precision=10,scale=7)
		BigDecimal longitude,
		@Column(precision=10,scale=7)
		BigDecimal latitude) {
	public GeoPosition {
		longitude = normalize(longitude, valueOf(-180), valueOf(180))
				.setScale(7, HALF_UP);
		latitude = normalize(latitude, valueOf(-90), valueOf(90))
				.setScale(7, HALF_UP);
	}

	private static BigDecimal normalize(BigDecimal input, BigDecimal min, BigDecimal max) {
		if (isBetween(input, min, max)) {
			return input;
		}
		var range = max.subtract(min);
		return input.subtract(input.divide(range, 0, UP).multiply(range));
	}

	private static boolean isBetween(BigDecimal input, BigDecimal min, BigDecimal max) {
		return input.compareTo(max) <= 0 && input.compareTo(min) >= 0;
	}
}
