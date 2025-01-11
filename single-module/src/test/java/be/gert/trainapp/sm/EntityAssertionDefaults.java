package be.gert.trainapp.sm;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm._shared.entity.JpaEntity;

public class EntityAssertionDefaults {
	private static final String[] AUDIT_FIELDS = {
			"createdDate",
			"lastModifiedDate",
			"lastModifiedBy",
			"createdBy",
			"version"
	};
	private static final String[] NESTED_AUDIT_FIELDS = {
			".*\\.createdDate",
			".*\\.lastModifiedDate",
			".*\\.lastModifiedBy",
			".*\\.createdBy",
			".*\\.version"
	};

	/**
	 * The hashCode/equals method of our entity is based on the ID, this means the tests must
	 * use recursive comparison to evaluate the state is as expected.
	 */
	public static <ID, E extends JpaEntity<ID>> RecursiveComparisonAssert<?> assertEntity(E entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
