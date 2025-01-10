package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.AUDIT_FIELDS;
import static be.gert.trainapp.sm.EntityAssertionDefaults.NESTED_AUDIT_FIELDS;
import static be.gert.trainapp.sm._shared.clock.AppClock.clock;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.planning._model.CertificationDefaults.certificateCodeTsiLoc;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm._shared.values.LocalDateRange;

public class TrainEngineerDefaults {

	public static TrainEngineerCertification trainEngineerCertification() {
		return new TrainEngineerCertification(
				new TrainEngineerCertificationId(employeeChristineGonzalesId, certificateCodeTsiLoc),
				new LocalDateRange(
						LocalDate.now(clock),
						LocalDate.now(clock).plusMonths(6)));
	}

	public static TrainEngineer trainEngineer() {
		return new TrainEngineer(
				employeeChristineGonzalesId,
				true,
				new ArrayList<>(List.of(trainEngineerCertification())));
	}


	public static RecursiveComparisonAssert<?> assertTrainEngineer(TrainEngineer entity) {
		return assertThat(entity)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(NESTED_AUDIT_FIELDS)
				.ignoringFields(AUDIT_FIELDS);
	}
}
