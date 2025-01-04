package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.Train.newTrain;
import static be.gert.trainapp.sm.planning._model.TrainExceptions.alreadyExists;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.planning.generated.CreateTrainUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.CreateTrainRequest;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class CreateTrainUseCase implements CreateTrainUseCaseApi {
	private final TrainJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(CreateTrainRequest request) {
		TrainId trainId = new TrainId(request.getTrainId());
		if (jpa.findById(trainId).isPresent()) {
			throw alreadyExists(trainId);
		}
		jpa.save(newTrain(trainId));
		return noContent().build();
	}
}
