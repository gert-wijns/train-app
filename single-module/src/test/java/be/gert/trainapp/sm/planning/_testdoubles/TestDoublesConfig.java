package be.gert.trainapp.sm.planning._testdoubles;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import be.gert.trainapp.sm.planning._port.SearchLocomotive;
import be.gert.trainapp.sm.planning._port.SearchWagon;

@Configuration
@Profile("module-core-test")
public class TestDoublesConfig {
	@Bean
	SearchLocomotive searchLocomotiveFake() {
		return Mockito.spy(new SearchLocomotiveFake());
	}

	@Bean
	SearchWagon searchWagonFake() {
		return Mockito.spy(new SearchWagonFake());
	}
}
