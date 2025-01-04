package be.gert.trainapp.sm;

public class EntityAssertionDefaults {
	public static final String[] AUDIT_FIELDS = {
			"createdDate",
			"lastModifiedDate",
			"lastModifiedBy",
			"createdBy",
			"version"
	};
	public static final String[] NESTED_AUDIT_FIELDS = {
			".*\\.createdDate",
			".*\\.lastModifiedDate",
			".*\\.lastModifiedBy",
			".*\\.createdBy",
			".*\\.version"
	};
}
