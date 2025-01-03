package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning.train.model.TrainExceptions.notFound;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.planning.generated.AddWagonToTrainUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.AddWagonToTrainRequest;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning.train.jpa.TrainJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddWagonToTrainUseCase implements AddWagonToTrainUseCaseApi {
	private final TrainJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddWagonToTrainRequest request) {
		var trainId = new TrainId(request.getTrainId());
		var wagonId = new WagonId(request.getWagonId());
		var train = jpa.findById(trainId)
				.orElseThrow(() -> notFound(trainId));

		jpa.save(train.addWagon(wagonId));

		return noContent().build();
	}
}
