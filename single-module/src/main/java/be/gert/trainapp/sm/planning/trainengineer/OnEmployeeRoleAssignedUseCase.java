package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.planning._model.TrainEngineer.newTrainEngineer;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import be.gert.trainapp.sm.personnel._event.EmployeeRoleAssigned;
import be.gert.trainapp.sm.personnel.EmployeeRole;
import be.gert.trainapp.sm.planning._repository.TrainEngineerJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OnEmployeeRoleAssignedUseCase {
	private final TrainEngineerJpaRepository jpa;

	@ApplicationModuleListener
	public void onEmployeeRoleAssigned(EmployeeRoleAssigned event) {
		var trainEngineerOpt = jpa.findById(event.id());
		if (event.role() == EmployeeRole.TRAIN_ENGINEER ) {
			jpa.save(trainEngineerOpt.orElseGet(() -> newTrainEngineer(event.id()))
					.activate());
		} else if (trainEngineerOpt.isPresent()) {
			jpa.save(trainEngineerOpt.get().inactivate());
		}
	}
}
