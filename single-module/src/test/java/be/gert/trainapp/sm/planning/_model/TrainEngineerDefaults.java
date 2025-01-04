package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.RecursiveComparisonAssert;

public class TrainEngineerDefaults {

	public static RecursiveComparisonAssert<?> assertTrainEngineer(TrainEngineer entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
