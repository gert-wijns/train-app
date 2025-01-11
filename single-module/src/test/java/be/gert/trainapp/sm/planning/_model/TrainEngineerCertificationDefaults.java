package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm._shared.clock.AppClock.clock;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.planning._model.CertificationDefaults.certificateCodeTsiLoc;

import java.time.LocalDate;

import be.gert.trainapp.sm._shared.values.LocalDateRange;

public class TrainEngineerCertificationDefaults {
	public static TrainEngineerCertification trainEngineerCertification() {
		return new TrainEngineerCertification(
				new TrainEngineerCertificationId(employeeChristineGonzalesId, certificateCodeTsiLoc),
				new LocalDateRange(
						LocalDate.now(clock),
						LocalDate.now(clock).plusMonths(6)));
	}
}
