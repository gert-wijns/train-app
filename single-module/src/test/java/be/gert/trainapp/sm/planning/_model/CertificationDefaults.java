package be.gert.trainapp.sm.planning._model;

public class CertificationDefaults {
	public static CertificateCode certificateCodeTsiLoc = new CertificateCode("TSI_LOC");
	public static CertificateCode certificateCodeTsiWag = new CertificateCode("TSI_WAG");
	public static CertificateCode certificateCodeNntrFrance = new CertificateCode("NNTR_FRANCE");

	public static Certificate certificateTsiLoc() {
		return new Certificate(certificateCodeTsiLoc,
				"Technical Specifications for Interoperability: Locomotives");
	}

	public static Certificate certificateTsiWag() {
		return new Certificate(certificateCodeTsiWag,
				"Technical Specifications for Interoperability: Wagons");
	}

	public static Certificate certificateNntrFrance() {
		return new Certificate(certificateCodeNntrFrance,
				"Notified National Technical Rules France");
	}

}
