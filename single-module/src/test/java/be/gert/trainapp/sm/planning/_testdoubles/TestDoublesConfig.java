package be.gert.trainapp.sm.planning._testdoubles;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("ModuleTest")
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

}
