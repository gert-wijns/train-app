package be.gert.trainapp.sm.usermanagement.user;

import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.usermanagement.generated.GrantRolesToUserUseCaseApi;
import be.gert.trainapp.api.usermanagement.generated.model.GrantRolesToUserRequest;
import be.gert.trainapp.sm.usermanagement.UserId;
import be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class GrantRolesToUserUseCase implements GrantRolesToUserUseCaseApi {
	private final UserJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(GrantRolesToUserRequest request) {
		var user = jpa.getById(new UserId(request.getUsername()));
		user.grantRoles(request.getRoles());
		jpa.save(user);
		return noContent().build();
	}
}
