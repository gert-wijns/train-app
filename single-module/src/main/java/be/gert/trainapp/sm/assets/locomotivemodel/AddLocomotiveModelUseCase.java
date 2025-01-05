package be.gert.trainapp.sm.assets.locomotivemodel;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.assets._model.LocomotiveModel.newLocomotiveModel;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.assets.generated.AddLocomotiveModelUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.AddLocomotiveModelRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.LocomotivePowerType;
import be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository;
import be.gert.trainapp.sm.network.TrackGauge;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddLocomotiveModelUseCase implements AddLocomotiveModelUseCaseApi {
	public static DomainException alreadyExists(LocomotiveModelId id) {
		return error("ASSETS_LOCOMOTIVE_MODEL_ALREADY_EXISTS",
				"Locomotive Model already exists for id '${id}'.")
				.withParam("id", id.id())
				.asException();
	}

	private final LocomotiveModelJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddLocomotiveModelRequest request) {
		var id = new LocomotiveModelId(request.getId());
		if (jpa.existsById(id)) {
			throw alreadyExists(id);
		}

		jpa.save(newLocomotiveModel(
				id,
				request.getName(),
				LocomotivePowerType.valueOf(request.getPowerType().getValue()),
				new TrackGauge(request.getGauge())));

		return noContent().build();
	}
}
