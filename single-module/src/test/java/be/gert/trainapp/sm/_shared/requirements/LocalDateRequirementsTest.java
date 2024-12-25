package be.gert.trainapp.sm._shared.requirements;

import static be.gert.trainapp.sm._shared.requirements.LocalDateRequirements.requireDate;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import be.gert.trainapp.sm._shared.clock.AppClock;

class LocalDateRequirementsTest {
	LocalDate earlier = now(AppClock.clock).minusDays(1);
	LocalDate now = now(AppClock.clock);
	LocalDate later = now(AppClock.clock);

	@Test
	void throwsWhenDateIsAfterDuringNotAfterCheck() {
		assertThatThrownBy(() -> requireDate(later).notAfter(earlier))
				.isEqualTo(new RequirementException("DATE_IS_AFTER"));
	}

	@Test
	void allowsEqualDateDuringNotAfter() {
		assertThatNoException().isThrownBy(() -> requireDate(now).notAfter(now));
	}
	@Test
	void allowsLaterDateDuringNotAfter() {
		assertThatNoException().isThrownBy(() -> requireDate(now).notAfter(later));
	}
}
