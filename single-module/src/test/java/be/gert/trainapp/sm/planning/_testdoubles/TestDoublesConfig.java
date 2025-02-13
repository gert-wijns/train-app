package be.gert.trainapp.sm.planning._testdoubles;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import be.gert.trainapp.sm.planning._adapter.SearchLocomotive;

@Configuration
@Profile("module-test")
public class TestDoublesConfig {

	/**
	 * Can create a spy bean of a fake if a one-off mockito.when is useful?
	 * (like when needing to throw an exception or so to test an edge case?)
	 */
	@Bean
	SearchWagonsQueryApiFake searchWagonsQueryApiFake() {
		return Mockito.spy(new SearchWagonsQueryApiFake());
	}

	@Bean
	SearchLocomotivesQueryApiFake searchLocomotivesQueryApiFake() {
		return Mockito.spy(new SearchLocomotivesQueryApiFake());
	}

	@Bean
	@Primary
	SearchLocomotive searchLocomotiveFake() {
		return Mockito.spy(new SearchLocomotiveFake());
	}
}
