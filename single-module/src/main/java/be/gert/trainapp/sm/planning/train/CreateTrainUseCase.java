package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.CONFLICT;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.planning._model.Train.newTrain;

import be.gert.trainapp.api.planning.generated.CreateTrainUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.CreateTrainRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._port.SearchLocomotive;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class CreateTrainUseCase implements CreateTrainUseCaseApi {
	private final TrainJpaRepository jpa;
	private final SearchLocomotive searchLocomotive;

	public static DomainException alreadyExists(TrainId trainId) {
		return error("PLANNING_TRAIN_ALREADY_EXISTS",
				"Train already exists for id '${id}'.")
				.withParam("id", trainId.id())
				.asException(CONFLICT);
	}

	@Override
	@Transactional
	public void execute(CreateTrainRequest request) {
		var trainId = new TrainId(request.getTrainId());
		var locomotiveId = new LocomotiveId(request.getLocomotiveId());
		if (jpa.findById(trainId).isPresent()) {
			throw alreadyExists(trainId);
		}
		var locomotive = searchLocomotive.getById(locomotiveId);
		var train = newTrain(trainId, locomotive);

		jpa.save(train);
	}
}
