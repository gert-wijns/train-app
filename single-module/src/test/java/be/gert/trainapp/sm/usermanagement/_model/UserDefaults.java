package be.gert.trainapp.sm.usermanagement._model;

import java.util.HashSet;
import java.util.Set;

import be.gert.trainapp.sm.usermanagement.UserId;

public class UserDefaults {
	public static UserId userRoseId = new UserId("Rose");

	public static User userRose() {
		return new User(
				userRoseId,
				"Tyler",
				new HashSet<>(Set.of("admin")));
	}

}
