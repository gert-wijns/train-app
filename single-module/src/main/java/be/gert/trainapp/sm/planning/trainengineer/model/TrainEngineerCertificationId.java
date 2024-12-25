package be.gert.trainapp.sm.planning.trainengineer.model;

import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning.certification.CertificateCode;
import jakarta.persistence.Embeddable;

@Embeddable
public record TrainEngineerCertificationId(EmployeeId employeeId, CertificateCode certificateCode) {
}
