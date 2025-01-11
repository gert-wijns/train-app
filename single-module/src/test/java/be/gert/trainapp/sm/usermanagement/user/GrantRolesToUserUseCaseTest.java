package be.gert.trainapp.sm.usermanagement.user;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.usermanagement._model.UserDefaults.userRose;
import static be.gert.trainapp.sm.usermanagement._model.UserDefaults.userRoseId;
import static be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository.notFound;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.usermanagement.generated.model.GrantRolesToUserRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository;

@ModuleTest
class GrantRolesToUserUseCaseTest {

	@Autowired
	UserJpaRepository jpa;
	@Autowired
	GrantRolesToUserUseCase usecase;

	GrantRolesToUserRequest request = new GrantRolesToUserRequest()
			.roles(List.of("assistant"))
			.username("Rose");

	@Test
	void success() {
		// given
		jpa.save(userRose());

		// when
		usecase.execute(request);

		// then
		assertEntity(jpa.getById(userRoseId))
				.isEqualTo(userRose().toBuilder()
						.roles(Set.of("admin", "assistant"))
						.build());
	}

	@Test
	void throwsNotFound() {
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(notFound(userRoseId));
	}
}
