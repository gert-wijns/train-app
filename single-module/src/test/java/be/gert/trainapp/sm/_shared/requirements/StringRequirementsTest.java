package be.gert.trainapp.sm._shared.requirements;

import static be.gert.trainapp.sm._shared.requirements.StringRequirements.requireNotBlank;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class StringRequirementsTest {

	@Test
	void throwsWhenBlank() {
		assertThatThrownBy(() -> requireNotBlank("  "))
				.isEqualTo(new RequirementException("STRING_IS_BLANK"));
	}

	@Test
	void okWhenNotBlank() {
		assertThatNoException().isThrownBy(() -> requireNotBlank(" ABC "));
	}
}
