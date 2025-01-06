package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.planning._model.Train.newTrain;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.planning.generated.CreateTrainUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.CreateTrainRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._adapter.SearchLocomotive;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class CreateTrainUseCase implements CreateTrainUseCaseApi {
	private final TrainJpaRepository jpa;
	private final SearchLocomotive searchLocomotive;

	public static DomainException alreadyExists(TrainId trainId) {
		return error("PLANNING_TRAIN_ALREADY_EXISTS",
				"Train already exists for id '${id}'.")
				.withParam("id", trainId.id())
				.asException();
	}

	@Override
	@Transactional
	public ResponseEntity<Void> execute(CreateTrainRequest request) {
		var trainId = new TrainId(request.getTrainId());
		var locomotiveId = new LocomotiveId(request.getLocomotiveId());
		if (jpa.findById(trainId).isPresent()) {
			throw alreadyExists(trainId);
		}
		var locomotive = searchLocomotive.getById(locomotiveId);
		var train = newTrain(trainId, locomotive);

		jpa.save(train);

		return noContent().build();
	}
}
