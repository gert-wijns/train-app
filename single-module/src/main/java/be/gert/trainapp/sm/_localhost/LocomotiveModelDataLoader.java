package be.gert.trainapp.sm._localhost;

import static be.gert.trainapp.api.assets.generated.model.LocomotivePowerType.ELECTRIC;
import static be.gert.trainapp.sm._localhost.NetworkDataLoader.standardGauge;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.assets.generated.model.AddLocomotiveModelRequest;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.locomotivemodel.AddLocomotiveModelUseCase;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class LocomotiveModelDataLoader {
	private final AddLocomotiveModelUseCase addLocomotiveModelUseCase;

	//https://www.brdatabase.info/locoqry.php?action=class&type=S&id=146005//
	static final LocomotiveModelId locomotiveModel_GWR4900 = new LocomotiveModelId("GWR_4900");
	//https://www.brdatabase.info/locoqry.php?action=class&type=S&id=146005//
	static final LocomotiveModelId locomotiveModel_GWR4073 = new LocomotiveModelId("GWR_4073");
	//https://www.brdatabase.info/locoqry.php?action=class&type=S&id=600201
	static final LocomotiveModelId locomotiveModel_LNERB1 = new LocomotiveModelId("LNR_B1");

	void loadModels() {
		addLocomotiveModelUseCase.execute(new AddLocomotiveModelRequest()
				.id(locomotiveModel_LNERB1.id())
				.name("LNERB1")
				.powerType(ELECTRIC)
				.gauge(standardGauge.type()));
		addLocomotiveModelUseCase.execute(new AddLocomotiveModelRequest()
				.id(locomotiveModel_GWR4073.id())
				.name("GWR4073")
				.powerType(ELECTRIC)
				.gauge(standardGauge.type()));
		addLocomotiveModelUseCase.execute(new AddLocomotiveModelRequest()
				.id(locomotiveModel_GWR4900.id())
				.name("GWR4900")
				.powerType(ELECTRIC)
				.gauge(standardGauge.type()));

	}
}
