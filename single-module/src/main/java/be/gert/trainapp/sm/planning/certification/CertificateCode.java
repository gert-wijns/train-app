package be.gert.trainapp.sm.planning.certification;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record CertificateCode(@Column(length = 36) String code) {
	public CertificateCode {
		requireNonNull(code);
	}
}
