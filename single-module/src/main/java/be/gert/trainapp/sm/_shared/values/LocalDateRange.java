package be.gert.trainapp.sm._shared.values;

import static be.gert.trainapp.sm._shared.requirements.LocalDateRequirements.requireDate;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;

@Embeddable
public record LocalDateRange(LocalDate startDate,
                             LocalDate endDate) {
	public LocalDateRange {
		requireNonNull(endDate);
		requireDate(startDate).notAfter(endDate);
	}
}
