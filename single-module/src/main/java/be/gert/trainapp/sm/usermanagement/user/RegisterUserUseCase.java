package be.gert.trainapp.sm.usermanagement.user;

import static org.springframework.http.ResponseEntity.noContent;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.usermanagement.generated.RegisterUserUseCaseApi;
import be.gert.trainapp.api.usermanagement.generated.model.RegisterUserRequest;
import be.gert.trainapp.sm.usermanagement.UserId;
import be.gert.trainapp.sm.usermanagement._model.User;
import be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class RegisterUserUseCase implements RegisterUserUseCaseApi {
	private final PasswordEncoder encoder;
	private final UserJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(RegisterUserRequest request) {
		var userId = new UserId(request.getUsername());
		if (jpa.existsById(userId)) {
			return ResponseEntity.badRequest().build();
		}

		String encodedPassword = encoder.encode(request.getPassword());
		jpa.save(new User(
				userId,
				encodedPassword,
				Set.of()));

		return noContent().build();
	}
}
