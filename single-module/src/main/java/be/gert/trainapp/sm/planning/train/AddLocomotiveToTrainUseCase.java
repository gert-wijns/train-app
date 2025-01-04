package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.TrainLocomotive.newTrainLocomotive;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.planning.generated.AddLocomotiveToTrainUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.AddLocomotiveToTrainRequest;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._model.TrainLocomotive;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddLocomotiveToTrainUseCase implements AddLocomotiveToTrainUseCaseApi {
	private final TrainJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddLocomotiveToTrainRequest request) {
		var trainId = new TrainId(request.getTrainId());
		var locomotiveId = new LocomotiveId(request.getLocomotiveId());
		var train = jpa.getById(trainId);

		TrainLocomotive locomotive = newTrainLocomotive(locomotiveId, new LocomotiveModelId("model-123"));
		jpa.save(train.addLocomotive(locomotive));

		return noContent().build();
	}
}
