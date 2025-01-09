package be.gert.trainapp.sm._localhost;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class LocalDataLoader {
	private final AuthenticationManager authenticationManager;
	private final EmployeeDataLoader employeeDataLoader;
	private final LocomotiveModelDataLoader locomotiveModelDataLoader;
	private final LocomotiveDataLoader locomotiveDataLoader;
	private final WagonDataLoader wagonDataLoader;
	private final NetworkDataLoader networkDataLoader;
	private final TrainDataLoader trainDataLoader;
	private final UserDataLoader userDataLoader;

	@EventListener(ApplicationStartedEvent.class)
	void onApplicationStarted() {
		userDataLoader.loadUsers();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken("System", "System"));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		employeeDataLoader.loadEmployees();
		locomotiveModelDataLoader.loadModels();
		locomotiveDataLoader.loadLocomotives();
		wagonDataLoader.loadWagons();
		networkDataLoader.loadNetworks();
		trainDataLoader.loadTrains();
	}
}

