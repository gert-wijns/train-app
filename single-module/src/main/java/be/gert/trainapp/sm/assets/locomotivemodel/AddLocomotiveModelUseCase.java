package be.gert.trainapp.sm.assets.locomotivemodel;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.CONFLICT;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.assets._model.LocomotiveModel.newLocomotiveModel;

import be.gert.trainapp.api.assets.generated.AddLocomotiveModelUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.AddLocomotiveModelRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.LocomotivePowerType;
import be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository;
import be.gert.trainapp.sm.network.TrackGauge;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class AddLocomotiveModelUseCase implements AddLocomotiveModelUseCaseApi {
	public static DomainException alreadyExists(LocomotiveModelId id) {
		return error("ASSETS_LOCOMOTIVE_MODEL_ALREADY_EXISTS",
				"Locomotive Model already exists for id '${id}'.")
				.withParam("id", id.id())
				.asException(CONFLICT);
	}

	private final LocomotiveModelJpaRepository jpa;

	@Override
	@Transactional
	public void execute(AddLocomotiveModelRequest request) {
		var id = new LocomotiveModelId(request.getId());
		if (jpa.existsById(id)) {
			throw alreadyExists(id);
		}

		jpa.save(newLocomotiveModel(
				id,
				request.getName(),
				LocomotivePowerType.valueOf(request.getPowerType().getValue()),
				new TrackGauge(request.getGauge())));
	}
}
