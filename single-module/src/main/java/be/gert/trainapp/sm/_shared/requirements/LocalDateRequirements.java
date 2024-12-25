package be.gert.trainapp.sm._shared.requirements;

import java.time.LocalDate;

public class LocalDateRequirements {
	public record LocalDateRequirement(LocalDate localDate) {
		public LocalDateRequirement notAfter(LocalDate other) {
			if (other != null && localDate.isAfter(other)) {
				throw new RequirementException("DATE_IS_AFTER");
			}
			return this;
		}
	}

	public static LocalDateRequirement requireDate(LocalDate localDate) {
		return new LocalDateRequirement(localDate);
	}
}
