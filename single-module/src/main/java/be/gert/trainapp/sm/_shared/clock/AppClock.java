package be.gert.trainapp.sm._shared.clock;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class AppClock {
	/**
	 * Fixed value unless app is started using the actual application launcher.
	 * See TrainApplication.
	 */
	public static Clock clock = Clock.fixed(
			OffsetDateTime.of(
							2024, 12, 20,
							12, 0, 0, 0,
							ZoneOffset.UTC)
					.toInstant(),
			ZoneId.ofOffset("UTC", ZoneOffset.UTC));
}
