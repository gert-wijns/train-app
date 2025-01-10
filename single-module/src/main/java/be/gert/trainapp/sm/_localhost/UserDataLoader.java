package be.gert.trainapp.sm._localhost;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.usermanagement.generated.GrantRolesToUserUseCaseApi;
import be.gert.trainapp.api.usermanagement.generated.RegisterUserUseCaseApi;
import be.gert.trainapp.api.usermanagement.generated.model.GrantRolesToUserRequest;
import be.gert.trainapp.api.usermanagement.generated.model.RegisterUserRequest;
import be.gert.trainapp.sm.personnel._model.PersonnelAuthRoles;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class UserDataLoader {
	private final RegisterUserUseCaseApi userRegistrationUseCaseApi;
	private final GrantRolesToUserUseCaseApi grantRolesToUserUseCaseApi;

	@SneakyThrows
	void loadUsers() {
		userRegistrationUseCaseApi.execute(new RegisterUserRequest()
				.username("David")
				.password("Tennant"));
		grantRolesToUserUseCaseApi.execute(new GrantRolesToUserRequest()
				.username("David")
				.roles(List.of("employee", "driver")));

		userRegistrationUseCaseApi.execute(new RegisterUserRequest()
				.username("Matt")
				.password("Smith"));
		grantRolesToUserUseCaseApi.execute(new GrantRolesToUserRequest()
				.username("Matt")
				.roles(List.of("employee", "driver")));

		userRegistrationUseCaseApi.execute(new RegisterUserRequest()
				.username("Eric")
				.password("Capaldi"));
		grantRolesToUserUseCaseApi.execute(new GrantRolesToUserRequest()
				.username("Eric")
				.roles(List.of("employee", "driver")));

		userRegistrationUseCaseApi.execute(new RegisterUserRequest()
				.username("Jodie")
				.password("Whittaker"));
		grantRolesToUserUseCaseApi.execute(new GrantRolesToUserRequest()
				.username("Jodie")
				.roles(List.of("employee", "driver")));

		userRegistrationUseCaseApi.execute(new RegisterUserRequest()
				.username("Michelle")
				.password("Gomez"));
		grantRolesToUserUseCaseApi.execute(new GrantRolesToUserRequest()
				.username("Michelle")
				.roles(List.of(PersonnelAuthRoles.HR)));

		userRegistrationUseCaseApi.execute(new RegisterUserRequest()
				.username("Clara")
				.password("Oswald"));
		grantRolesToUserUseCaseApi.execute(new GrantRolesToUserRequest()
				.username("Clara")
				.roles(List.of()));

		userRegistrationUseCaseApi.execute(new RegisterUserRequest()
				.username("Rose")
				.password("Tyler"));
		grantRolesToUserUseCaseApi.execute(new GrantRolesToUserRequest()
				.username("Rose")
				.roles(List.of(PersonnelAuthRoles.ADMIN)));

		userRegistrationUseCaseApi.execute(new RegisterUserRequest()
				.username("River")
				.password("Song"));
		grantRolesToUserUseCaseApi.execute(new GrantRolesToUserRequest()
				.username("River")
				.roles(List.of()));

		userRegistrationUseCaseApi.execute(new RegisterUserRequest()
				.username("System")
				.password("System"));
		grantRolesToUserUseCaseApi.execute(new GrantRolesToUserRequest()
				.username("System")
				.roles(List.of(PersonnelAuthRoles.ADMIN)));

	}

}
