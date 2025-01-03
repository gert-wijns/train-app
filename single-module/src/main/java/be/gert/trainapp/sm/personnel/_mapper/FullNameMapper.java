package be.gert.trainapp.sm.personnel._mapper;

import be.gert.trainapp.api.personnel.generated.model.FullNameBody;
import be.gert.trainapp.sm._shared.values.FullName;

public class FullNameMapper {

	public static FullName toFullName(FullNameBody fullNameBody) {
		return fullNameBody == null ? null:
				new FullName(fullNameBody.getFirstName(), fullNameBody.getLastName());
	}

	public static FullNameBody toFullNameBody(FullName fullName) {
		return fullName == null ? null:
				new FullNameBody()
					.firstName(fullName.firstName())
					.lastName(fullName.lastName());
	}
}
