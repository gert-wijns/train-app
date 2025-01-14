package be.gert.trainapp.sm.planning.train;

import be.gert.trainapp.api.planning.generated.AddWagonToTrainUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.AddWagonToTrainRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._adapter.SearchWagon;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class AddWagonToTrainUseCase implements AddWagonToTrainUseCaseApi {
	private final TrainJpaRepository jpa;
	private final SearchWagon searchWagon;

	@Override
	@Transactional
	public void execute(AddWagonToTrainRequest request) {
		var trainId = new TrainId(request.getTrainId());
		var wagonId = new WagonId(request.getWagonId());
		var train = jpa.getById(trainId);

		var wagon = searchWagon.getById(wagonId);
		jpa.save(train.attachWagon(wagon));
	}
}
