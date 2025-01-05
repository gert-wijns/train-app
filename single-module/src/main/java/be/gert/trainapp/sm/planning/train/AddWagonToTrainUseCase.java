package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.TrainWagon.newTrainWagon;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.ResponseEntity.noContent;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.assets.generated.SearchWagonsQueryApi;
import be.gert.trainapp.api.planning.generated.AddWagonToTrainUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.AddWagonToTrainRequest;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._model.TrainWagon;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddWagonToTrainUseCase implements AddWagonToTrainUseCaseApi {
	private final TrainJpaRepository jpa;
	private final SearchWagonsQueryApi searchWagonsQueryApi;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddWagonToTrainRequest request) {
		var trainId = new TrainId(request.getTrainId());
		var wagonId = new WagonId(request.getWagonId());
		var train = jpa.getById(trainId);

		var wagonResponse = requireNonNull(searchWagonsQueryApi
				.query(List.of(request.getWagonId())).getBody())
				.getFirst();
		TrainWagon wagon = newTrainWagon(wagonId, new WagonModelId(wagonResponse.getModelTypeId()));
		jpa.save(train.attachWagon(wagon));

		return noContent().build();
	}
}
