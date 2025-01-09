package be.gert.trainapp.sm.usermanagement._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.usermanagement.UserId;
import be.gert.trainapp.sm.usermanagement._model.User;

@Repository
public interface UserJpaRepository extends CrudRepository<User, UserId> {
	static DomainException notFound(UserId id) {
		return error("USER_MANAGEMENT_USER_NOT_FOUND",
				"User not found id '${id}'.")
				.withParam("id", id.id())
				.asException();
	}

	default User getById(UserId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
