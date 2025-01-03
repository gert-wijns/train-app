package be.gert.trainapp.sm.network;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GeoPositionTest {

	record Input(String longitude, String latitude) {
	}
	record Normalized(String longitude, String latitude) {
	}

	@ParameterizedTest
	@MethodSource("normalizesInput")
	void normalizes(String description, Input input, Normalized output) {
		assertThat(new GeoPosition(new BigDecimal(input.longitude), new BigDecimal(input.latitude)))
				.describedAs(description)
				.extracting("longitude", "latitude")
				.containsExactly(new BigDecimal(output.longitude), new BigDecimal(output.latitude));
	}

	static List<Arguments> normalizesInput() {
		return List.of(
				Arguments.of(
						"Should set scale to 7",
						new Input("1", "2"),
						new Normalized("1.0000000", "2.0000000")),
				Arguments.of(
						"Should recalculate longitude to have a value in range [-180,180]",
						new Input("950", "0"),
						new Normalized("-130.0000000", "0.0000000")),
				Arguments.of(
						"Should recalculate longitude to have a value in range [-180,180]",
						new Input("-950", "0"),
						new Normalized("130.0000000", "0.0000000")),
				Arguments.of(
						"Should recalculate latitude to have a value in range [-90,90]",
						new Input("0", "460"),
						new Normalized("0.0000000", "-80.0000000")),
				Arguments.of(
						"Should recalculate latitude to have a value in range [-90,90]",
						new Input("0", "-460"),
						new Normalized("0.0000000", "80.0000000"))
		);
	}
}
