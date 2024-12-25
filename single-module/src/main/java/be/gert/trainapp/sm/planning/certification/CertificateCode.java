package be.gert.trainapp.sm.planning.certification;

import jakarta.persistence.Embeddable;

@Embeddable
public record CertificateCode(String code) {
}
