package be.gert.trainapp.sm._shared.values;

import static be.gert.trainapp.sm._shared.requirements.StringRequirements.requireNotBlank;

import jakarta.persistence.Embeddable;

@Embeddable
public record FullName(String firstName, String lastName) {
	public FullName {
		requireNotBlank(firstName);
		requireNotBlank(lastName);
		firstName = firstName.trim();
		lastName = lastName.trim();
	}
}
