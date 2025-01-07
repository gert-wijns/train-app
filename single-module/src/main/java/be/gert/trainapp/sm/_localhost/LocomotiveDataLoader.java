package be.gert.trainapp.sm._localhost;

import static be.gert.trainapp.sm._localhost.LocomotiveModelDataLoader.locomotiveModel_GWR4073;
import static be.gert.trainapp.sm._localhost.LocomotiveModelDataLoader.locomotiveModel_GWR4900;
import static be.gert.trainapp.sm._localhost.LocomotiveModelDataLoader.locomotiveModel_LNERB1;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.assets.generated.model.AddLocomotiveRequest;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.locomotive.AddLocomotiveUseCase;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class LocomotiveDataLoader {
	private final AddLocomotiveUseCase addLocomotiveUseCase;

	static final LocomotiveId locomotiveAHaroldBibby = new LocomotiveId("1250");
	static final LocomotiveId locomotiveAbbotsburyCastle = new LocomotiveId("4083");
	static final LocomotiveId locomotiveAbberleyHall = new LocomotiveId("4981");

	void loadLocomotives() {
		addLocomotiveUseCase.execute(new AddLocomotiveRequest()
				.id(locomotiveAHaroldBibby.id())
				.modelTypeId(locomotiveModel_LNERB1.id())
				.name("A HAROLD BIBBY")
				.serialNumber("2024-364-0600-01"));
		addLocomotiveUseCase.execute(new AddLocomotiveRequest()
				.id(locomotiveAbbotsburyCastle.id())
				.modelTypeId(locomotiveModel_GWR4073.id())
				.name("ABBOTSBURY CASTLE")
				.serialNumber("2024-364-0600-02"));
		addLocomotiveUseCase.execute(new AddLocomotiveRequest()
				.id(locomotiveAbberleyHall.id())
				.modelTypeId(locomotiveModel_GWR4900.id())
				.name("ABBERLEY HALL")
				.serialNumber("2024-364-0600-03"));

	}
}
