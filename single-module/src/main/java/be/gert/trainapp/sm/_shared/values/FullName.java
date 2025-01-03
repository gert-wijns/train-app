package be.gert.trainapp.sm._shared.values;

import static be.gert.trainapp.sm._shared.requirements.StringRequirements.requireNotBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record FullName(@Column(length = 36) String firstName, @Column(length = 36) String lastName) {
	public FullName {
		requireNotBlank(firstName);
		requireNotBlank(lastName);
		firstName = firstName.trim();
		lastName = lastName.trim();
	}
}
