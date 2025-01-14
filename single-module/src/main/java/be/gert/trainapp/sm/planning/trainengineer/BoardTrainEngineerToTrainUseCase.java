package be.gert.trainapp.sm.planning.trainengineer;

import be.gert.trainapp.api.planning.generated.BoardTrainEngineerToTrainUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.BoardTrainEngineerToTrainRequest;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._repository.TrainEngineerJpaRepository;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class BoardTrainEngineerToTrainUseCase implements BoardTrainEngineerToTrainUseCaseApi {
	private final TrainJpaRepository jpa;
	private final TrainEngineerJpaRepository trainEngineerJpa;

	@Override
	@Transactional
	public void execute(BoardTrainEngineerToTrainRequest request) {
		var train = jpa.getById(new TrainId(request.getTrainId()));
		var trainEngineer = trainEngineerJpa.getById(new EmployeeId(request.getEmployeeId()));
		jpa.save(train.boardTrainEngineer(trainEngineer));
	}
}
