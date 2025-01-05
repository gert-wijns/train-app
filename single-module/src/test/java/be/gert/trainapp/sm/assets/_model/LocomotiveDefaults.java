package be.gert.trainapp.sm.assets._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5Id;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.SerialNumber;

public class LocomotiveDefaults {
	public static final LocomotiveId locomotive1937Id = new LocomotiveId("locomotive-1");

	public static final SerialNumber serialNumberStainier = new SerialNumber("Stainier-SN");

	public static Locomotive locomotiveStainier() {
		return new Locomotive(
				locomotive1937Id,
				locomotiveModelLMSStainierBlack5Id,
				"Stainier",
				serialNumberStainier,
				false);
	}

	public static RecursiveComparisonAssert<?> assertLocomotive(Locomotive entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
