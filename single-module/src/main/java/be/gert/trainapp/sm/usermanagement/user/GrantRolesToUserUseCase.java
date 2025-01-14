package be.gert.trainapp.sm.usermanagement.user;

import be.gert.trainapp.api.usermanagement.generated.GrantRolesToUserUseCaseApi;
import be.gert.trainapp.api.usermanagement.generated.model.GrantRolesToUserRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.usermanagement.UserId;
import be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class GrantRolesToUserUseCase implements GrantRolesToUserUseCaseApi {
	private final UserJpaRepository jpa;

	@Override
	@Transactional
	public void execute(GrantRolesToUserRequest request) {
		var user = jpa.getById(new UserId(request.getUsername()));
		user.grantRoles(request.getRoles());
		jpa.save(user);
	}
}
