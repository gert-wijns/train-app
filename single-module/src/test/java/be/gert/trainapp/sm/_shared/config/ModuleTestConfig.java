package be.gert.trainapp.sm._shared.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

@Configuration
@Import({
		ModuleTestAutowireCandidateResolverConfigurer.class,
		SyncTaskExecutorTestConfig.class})
@Profile("module-test")
public class ModuleTestConfig {
	UserDetailsServiceFake userDetailsServiceFake = Mockito.spy(new UserDetailsServiceFake());

	@Bean
	@Primary
	public UserDetailsServiceFake userDetailsServiceFake() {
		return userDetailsServiceFake;
	}

	@BeforeTestMethod
	void reset() {
		Mockito.reset(userDetailsServiceFake);
	}
}
