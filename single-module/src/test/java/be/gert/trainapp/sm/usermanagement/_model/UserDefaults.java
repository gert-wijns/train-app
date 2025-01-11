package be.gert.trainapp.sm.usermanagement._model;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.RecursiveComparisonAssert;

import be.gert.trainapp.sm.usermanagement.UserId;

public class UserDefaults {
	public static UserId userRoseId = new UserId("Rose");

	public static User userRose() {
		return User.builder()
				.id(userRoseId)
				.password("Tyler")
				.roles(new HashSet<>(Set.of("admin")))
				.build();
	}

}
