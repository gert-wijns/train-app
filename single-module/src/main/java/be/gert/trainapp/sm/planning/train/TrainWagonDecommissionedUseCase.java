package be.gert.trainapp.sm.planning.train;

import org.springframework.modulith.events.ApplicationModuleListener;

import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import be.gert.trainapp.sm.assets._events.WagonDecommissioned;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class TrainWagonDecommissionedUseCase {
	private final TrainJpaRepository trainJpaRepository;

	@ApplicationModuleListener
	public void onWagonDecommissioned(WagonDecommissioned wagon) {
		var trainOpt = trainJpaRepository.findByWagonId(wagon.id());
		if (trainOpt.isPresent()) {
			trainOpt.get().wagonDecommissioned(wagon.id());
		}
	}

}
