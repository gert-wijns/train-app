package be.gert.trainapp.sm._shared.requirements;

public class StringRequirements {

	public static void requireNotBlank(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new RequirementException("STRING_IS_BLANK");
		}
	}
}
