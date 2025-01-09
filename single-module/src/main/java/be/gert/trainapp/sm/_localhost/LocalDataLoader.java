package be.gert.trainapp.sm._localhost;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import be.gert.trainapp.sm.personnel._model.PersonnelAuthRoles;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class LocalDataLoader {
	private final EmployeeDataLoader employeeDataLoader;
	private final LocomotiveModelDataLoader locomotiveModelDataLoader;
	private final LocomotiveDataLoader locomotiveDataLoader;
	private final WagonDataLoader wagonDataLoader;
	private final NetworkDataLoader networkDataLoader;
	private final TrainDataLoader trainDataLoader;
	private final UserDataLoader userDataLoader;

	@EventListener(ApplicationStartedEvent.class)
	void onApplicationStarted() {
		asSystemUser();
		employeeDataLoader.loadEmployees();
		locomotiveModelDataLoader.loadModels();
		locomotiveDataLoader.loadLocomotives();
		wagonDataLoader.loadWagons();
		networkDataLoader.loadNetworks();
		trainDataLoader.loadTrains();
		userDataLoader.loadUsers();
	}

	private void asSystemUser() {
		var authorities = Stream.of(requireNonNull(
						PersonnelAuthRoles.ADMIN))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
		var token = new UsernamePasswordAuthenticationToken(
				"System", "System", authorities);
		SecurityContextHolder.getContext().setAuthentication(token);
	}
}

