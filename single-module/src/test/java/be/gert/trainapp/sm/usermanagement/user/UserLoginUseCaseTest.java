package be.gert.trainapp.sm.usermanagement.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import be.gert.trainapp.api.usermanagement.generated.model.RegisterUserRequest;
import be.gert.trainapp.api.usermanagement.generated.model.UserLoginRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm._shared.testdoubles.UserDetailsServiceFake;
import be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository;

@ModuleTest
class UserLoginUseCaseTest {

	@Autowired
	UserJpaRepository jpa;
	@Autowired
	UserLoginUseCase usecase;
	@Autowired
	RegisterUserUseCase registerUserUseCase;
	@Autowired
	UserDetailsServiceFake spy;

	RegisterUserRequest registerRequest = new RegisterUserRequest()
			.password("Tyler")
			.username("Rose");
	UserLoginRequest request = new UserLoginRequest()
			.username("Rose")
			.password("Tyler");

	@Test
	void success() {
		// given using register because of password encryption
		registerUserUseCase.execute(registerRequest);

		// when
		doReturn(jpa.loadUserByUsername("Rose")).when(spy).loadUserByUsername("Rose");
		var response = usecase.execute(request);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void throwsBadCredentialsWhenIncorrectPassword() {
		// given
		registerUserUseCase.execute(registerRequest.password("-1"));

		// when
		doReturn(jpa.loadUserByUsername("Rose")).when(spy).loadUserByUsername("Rose");
		assertThatThrownBy(() -> usecase.execute(request))
				.isInstanceOf(BadCredentialsException.class);
	}

	@Test
	void throwsBadCredentialsWhenUserDoesntExist() {
		doThrow(new UsernameNotFoundException("User not found")).when(spy).loadUserByUsername("Rose");
		assertThatThrownBy(() -> usecase.execute(request))
				.isInstanceOf(BadCredentialsException.class);
	}
}
