package be.gert.trainapp.sm.planning._model;

import static java.util.Objects.requireNonNull;

import be.gert.trainapp.sm.personnel.EmployeeId;
import jakarta.persistence.Embeddable;

@Embeddable
public record TrainEngineerCertificationId(EmployeeId employeeId, CertificateCode certificateCode) {
	public TrainEngineerCertificationId {
		requireNonNull(employeeId);
		requireNonNull(certificateCode);
	}
}
