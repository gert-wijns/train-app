package be.gert.trainapp.sm._localhost;

import static be.gert.trainapp.sm._localhost.EmployeeDataLoader.employeeChristinaId;
import static be.gert.trainapp.sm._localhost.LocomotiveDataLoader.locomotiveAHaroldBibby;
import static be.gert.trainapp.sm._localhost.LocomotiveDataLoader.locomotiveAbbotsburyCastle;
import static be.gert.trainapp.sm._localhost.WagonDataLoader.wagonCoachId;
import static be.gert.trainapp.sm._localhost.WagonDataLoader.wagonGondolaId;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.planning.generated.model.AddWagonToTrainRequest;
import be.gert.trainapp.api.planning.generated.model.BoardTrainEngineerToTrainRequest;
import be.gert.trainapp.api.planning.generated.model.CreateTrainRequest;
import be.gert.trainapp.sm.planning.TrainId;
import be.gert.trainapp.sm.planning.train.AddWagonToTrainUseCase;
import be.gert.trainapp.sm.planning.train.CreateTrainUseCase;
import be.gert.trainapp.sm.planning.trainengineer.BoardTrainEngineerToTrainUseCase;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class TrainDataLoader {
	private final CreateTrainUseCase createTrainUseCase;
	private final AddWagonToTrainUseCase addWagonToTrainUseCase;
	private final BoardTrainEngineerToTrainUseCase boardTrainEngineerToTrainUseCase;

	static final TrainId trainAbbotsburyId = new TrainId("train-abbotsbury");
	static final TrainId trainHaroldId = new TrainId("train-harold");

	void loadTrains() {
		createTrainUseCase.execute(new CreateTrainRequest()
				.trainId(trainHaroldId.id())
				.locomotiveId(locomotiveAHaroldBibby.id()));
		for (int i=1; i <= 3; i++) {
			addWagonToTrainUseCase.execute(new AddWagonToTrainRequest()
					.trainId(trainHaroldId.id())
					.wagonId(wagonCoachId(i).id()));
		}
		boardTrainEngineerToTrainUseCase.execute(new BoardTrainEngineerToTrainRequest()
				.trainId(trainHaroldId.id())
				.employeeId(employeeChristinaId.id()));

		createTrainUseCase.execute(new CreateTrainRequest()
				.trainId(trainAbbotsburyId.id())
				.locomotiveId(locomotiveAbbotsburyCastle.id()));
		addWagonToTrainUseCase.execute(new AddWagonToTrainRequest()
				.trainId(trainAbbotsburyId.id())
				.wagonId(wagonGondolaId(1).id()));
	}
}
