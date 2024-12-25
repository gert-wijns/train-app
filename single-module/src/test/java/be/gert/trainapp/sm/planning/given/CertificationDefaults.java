package be.gert.trainapp.sm.planning.given;

import be.gert.trainapp.sm.planning.certification.CertificateCode;
import be.gert.trainapp.sm.planning.certification.model.Certificate;

public class CertificationDefaults {
	public static CertificateCode certificateCodeTsiLoc = new CertificateCode("TSI_LOC");
	public static CertificateCode certificateCodeTsiWag = new CertificateCode("TSI_WAG");
	public static CertificateCode certificateCodeNntrFrance = new CertificateCode("NNTR_FRANCE");

	public static Certificate certificateTsiLoc() {
		return new Certificate()
				.withId(certificateCodeTsiLoc)
				.withDescription("Technical Specifications for Interoperability: Locomotives");
	}

	public static Certificate certificateTsiWag() {
		return new Certificate()
				.withId(certificateCodeTsiWag)
				.withDescription("Technical Specifications for Interoperability: Wagons");
	}

	public static Certificate certificateNntrFrance() {
		return new Certificate()
				.withId(certificateCodeNntrFrance)
				.withDescription("Notified National Technical Rules France");
	}

}
