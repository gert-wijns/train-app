package be.gert.trainapp.sm.planning._events;

import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning._model.CertificateCode;
import jakarta.annotation.Nonnull;

public record TrainEngineerCertificationRegistered(
		@Nonnull EmployeeId employeeId,
		@Nonnull CertificateCode certificate) implements TrainEngineerCertificationEvent {
}
