package be.gert.trainapp.sm.planning.trainengineer.model.events;

import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning.certification.CertificateCode;
import jakarta.annotation.Nonnull;

public record TrainEngineerCertificationRegistered(
		@Nonnull EmployeeId employeeId,
		@Nonnull CertificateCode certificate) implements TrainEngineerCertificationEvent {
}
