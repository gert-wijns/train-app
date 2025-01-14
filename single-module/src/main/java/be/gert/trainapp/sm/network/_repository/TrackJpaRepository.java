package be.gert.trainapp.sm.network._repository;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.NOT_FOUND;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.KeyParam;
import be.gert.trainapp.sm.network.TrackId;
import be.gert.trainapp.sm.network._model.Track;

@Repository
public interface TrackJpaRepository extends CrudRepository<Track, TrackId> {
	static DomainException notFound(TrackId trackId) {
		return error("NOT_FOUND", "${entity} not found for '${fromId}' and '${toId}'.")
				.withParam("entity", KeyParam.key("TRACK"))
				.withParam("fromId", trackId.from().id())
				.withParam("toId", trackId.to().id())
				.asException(NOT_FOUND);
	}

	default Track getById(TrackId id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
