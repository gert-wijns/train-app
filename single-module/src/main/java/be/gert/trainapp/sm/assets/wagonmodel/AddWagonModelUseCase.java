package be.gert.trainapp.sm.assets.wagonmodel;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.CONFLICT;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.assets._model.WagonModel.newWagonModel;

import be.gert.trainapp.api.assets.generated.AddWagonModelUseCaseApi;
import be.gert.trainapp.api.assets.generated.model.AddWagonModelRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets.WagonType;
import be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository;
import be.gert.trainapp.sm.network.TrackGauge;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class AddWagonModelUseCase implements AddWagonModelUseCaseApi {

	public static DomainException alreadyExists(WagonModelId id) {
		return error("ASSETS_WAGON_MODEL_ALREADY_EXISTS",
				"Wagon Model already exists for id '${id}'.")
				.withParam("id", id.id())
				.asException(CONFLICT);
	}

	private final WagonModelJpaRepository jpa;

	@Override
	@Transactional
	public void execute(AddWagonModelRequest request) {
		var id = new WagonModelId(request.getId());
		if (jpa.existsById(id)) {
			throw alreadyExists(id);
		}

		jpa.save(newWagonModel(
				id,
				request.getName(),
				new TrackGauge(request.getGauge()),
				WagonType.valueOf(request.getType().getValue())));
	}
}
