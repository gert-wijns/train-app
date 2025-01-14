package be.gert.trainapp.sm.usermanagement._repository;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.NOT_FOUND;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.KeyParam;
import be.gert.trainapp.sm.usermanagement.UserId;
import be.gert.trainapp.sm.usermanagement._model.User;

@Repository
public interface UserJpaRepository extends CrudRepository<User, UserId>, UserDetailsService {
	static DomainException notFound(UserId id) {
		return error("NOT_FOUND", "${entity} not found id '${id}'.")
				.withParam("entity", KeyParam.key("USER"))
				.withParam("id", id.id())
				.asException(NOT_FOUND);
	}

	default User getById(UserId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}

	@Override
	default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = findById(new UserId(username))
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(
				user.id().id(),
				user.password(),
				user.roles().stream()
						.map(SimpleGrantedAuthority::new)
						.toList());
	}
}
