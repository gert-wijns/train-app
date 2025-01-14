package be.gert.trainapp.sm.planning.train;

import org.springframework.modulith.events.ApplicationModuleListener;

import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets._events.LocomotiveDecommissioned;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class TrainLocomotiveDecommissionedUseCase {
	private final TrainJpaRepository jpa;

	@ApplicationModuleListener
	public void onLocomotiveDecommissioned(LocomotiveDecommissioned event) {
		var trainOpt = jpa.findByLocomotiveId(event.locomotiveId());
		if (trainOpt.isPresent()) {
			jpa.save(trainOpt.get().locomotiveDecommissioned());
		}
	}

}
