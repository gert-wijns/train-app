package be.gert.trainapp.sm._shared.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import be.gert.trainapp.sm._shared.testdoubles.UserDetailsServiceFake;

@Configuration
@Import({
		SyncTaskExecutorTestConfig.class})
@Profile("module-core-test")
public class ModuleTestConfig {

	@Bean
	@Primary
	public UserDetailsServiceFake userDetailsServiceFake() {
		return Mockito.spy(new UserDetailsServiceFake());
	}

}
