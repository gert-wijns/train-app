package be.gert.trainapp.sm.assets.wagonmodel;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.assets._model.WagonModel.newWagonModel;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.assets.generated.AddWagonModelUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.AddWagonModelRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository;
import be.gert.trainapp.sm.network.TrackGauge;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddWagonModelUseCase implements AddWagonModelUseCaseApi {

	public static DomainException alreadyExists(WagonModelId id) {
		return error("ASSETS_WAGON_MODEL_ALREADY_EXISTS",
				"Wagon Model already exists for id '${id}'.")
				.withParam("id", id.id())
				.asException();
	}

	private final WagonModelJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddWagonModelRequest request) {
		var id = new WagonModelId(request.getId());
		if (jpa.existsById(id)) {
			throw alreadyExists(id);
		}

		jpa.save(newWagonModel(
				id,
				request.getName(),
				new TrackGauge(request.getGauge())));

		return noContent().build();
	}
}
