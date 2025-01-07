package be.gert.trainapp.sm._localhost;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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

	@EventListener(ApplicationStartedEvent.class)
	void onApplicationStarted() {
		employeeDataLoader.loadEmployees();
		locomotiveModelDataLoader.loadModels();
		locomotiveDataLoader.loadLocomotives();
		wagonDataLoader.loadWagons();
		networkDataLoader.loadNetworks();
		trainDataLoader.loadTrains();
	}
}

