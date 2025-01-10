package be.gert.trainapp.sm.usermanagement._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.usermanagement.UserId;

public class UserDefaults {
	public static UserId userRoseId = new UserId("Rose");

	public static User userRose() {
		return User.builder()
				.id(userRoseId)
				.password("Tyler")
				.roles(Set.of("admin"))
				.build();
	}

	public static RecursiveComparisonAssert<?> assertUser(User entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
