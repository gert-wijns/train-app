package be.gert.trainapp.sm.usermanagement.user;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.CONFLICT;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import be.gert.trainapp.api.usermanagement.generated.RegisterUserUseCaseApi;
import be.gert.trainapp.api.usermanagement.generated.model.RegisterUserRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.usermanagement.UserId;
import be.gert.trainapp.sm.usermanagement._model.User;
import be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class RegisterUserUseCase implements RegisterUserUseCaseApi {
	public static DomainException alreadyExists(UserId userId) {
		return error("USERMANAGEMENT_USER_ALREADY_EXISTS",
				"User already exists for id '${id}'.")
				.withParam("id", userId.id())
				.asException(CONFLICT);
	}
	private final PasswordEncoder encoder;
	private final UserJpaRepository jpa;

	@Override
	@Transactional
	public void execute(RegisterUserRequest request) {
		var userId = new UserId(request.getUsername());
		if (jpa.existsById(userId)) {
			throw alreadyExists(userId);
		}

		String encodedPassword = encoder.encode(request.getPassword());
		jpa.save(new User(
				userId,
				encodedPassword,
				Set.of()));
	}
}
