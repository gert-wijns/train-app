package be.gert.trainapp.sm.planning.given;

import be.gert.trainapp.sm.planning.certification.CertificateCode;
import be.gert.trainapp.sm.planning.certification.model.Certificate;

public class CertificationDefaults {
	public static CertificateCode certificateCodeTsiLoc = new CertificateCode("TSI_LOC");
	public static CertificateCode certificateCodeTsiWag = new CertificateCode("TSI_WAG");
	public static CertificateCode certificateCodeNntrFrance = new CertificateCode("NNTR_FRANCE");

	public static Certificate certificateTsiLoc() {
		return Certificate.builder()
				.id(certificateCodeTsiLoc)
				.description("Technical Specifications for Interoperability: Locomotives")
				.build();
	}

	public static Certificate certificateTsiWag() {
		return Certificate.builder()
				.id(certificateCodeTsiWag)
				.description("Technical Specifications for Interoperability: Wagons")
				.build();
	}

	public static Certificate certificateNntrFrance() {
		return Certificate.builder()
				.id(certificateCodeNntrFrance)
				.description("Notified National Technical Rules France")
				.build();
	}

}
