package be.gert.trainapp.sm.usermanagement.user;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.usermanagement._model.UserDefaults.userRose;
import static be.gert.trainapp.sm.usermanagement._model.UserDefaults.userRoseId;
import static be.gert.trainapp.sm.usermanagement.user.RegisterUserUseCase.alreadyExists;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.usermanagement.generated.model.RegisterUserRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository;

@ModuleTest
class RegisterUserUseCaseTest {

	@Autowired
	UserJpaRepository jpa;
	@Autowired
	RegisterUserUseCase usecase;

	RegisterUserRequest request = new RegisterUserRequest()
			.password("Tyler")
			.username("Rose");

	@Test
	void success() {
		// when
		usecase.execute(request);

		// then
		assertEntity(jpa.getById(userRoseId)).isNotNull();
	}

	@Test
	void throwsAlreadyExists() {
		jpa.save(userRose());

		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(userRoseId));
	}
}
