package be.gert.trainapp.sm.planning.trainengineer;

import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.planning.generated.BoardTrainEngineerToTrainUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.BoardTrainEngineerToTrainRequest;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning._repository.TrainEngineerJpaRepository;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class BoardTrainEngineerToTrainUseCase implements BoardTrainEngineerToTrainUseCaseApi {
	private final TrainJpaRepository jpa;
	private final TrainEngineerJpaRepository trainEngineerJpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(BoardTrainEngineerToTrainRequest request) {
		var train = jpa.getById(new TrainId(request.getTrainId()));
		var trainEngineer = trainEngineerJpa.getById(new EmployeeId(request.getEmployeeId()));
		jpa.save(train.boardTrainEngineer(trainEngineer));
		return noContent().build();
	}
}
