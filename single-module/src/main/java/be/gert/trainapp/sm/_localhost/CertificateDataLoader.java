package be.gert.trainapp.sm._localhost;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.gert.trainapp.sm.planning._model.Certificate;
import be.gert.trainapp.sm.planning._model.CertificateCode;
import be.gert.trainapp.sm.planning._repository.CertificateJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class CertificateDataLoader {
	public static final CertificateCode certificateCodeTsiLoc = new CertificateCode("TSI_LOC");
	public static final CertificateCode certificateCodeTsiWag = new CertificateCode("TSI_WAG");
	public static final CertificateCode certificateCodeNntrFrance = new CertificateCode("NNTR_FRANCE");

	private final CertificateJpaRepository certificateJpa;

	public void loadCertificates() {
		certificateJpa.save(new Certificate(certificateCodeTsiLoc,
				"Technical Specifications for Interoperability: Locomotives"));
		certificateJpa.save(new Certificate(certificateCodeTsiWag,
				"Technical Specifications for Interoperability: Wagons"));
		certificateJpa.save(new Certificate(certificateCodeNntrFrance,
				"Notified National Technical Rules France"));
	}
}
