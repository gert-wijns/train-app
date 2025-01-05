package be.gert.trainapp.sm.assets.locomotivemodel;

import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.assertLocomotiveModel;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5Id;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5Name;
import static be.gert.trainapp.sm.assets.locomotivemodel.AddLocomotiveModelUseCase.alreadyExists;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.assets.generated.model.AddLocomotiveModelRequest;
import be.gert.trainapp.api.assets.generated.model.LocomotivePowerType;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository;

@ModuleTest
class AddLocomotiveModelUseCaseTest {
	@Autowired
	LocomotiveModelJpaRepository jpa;
	@Autowired
	AddLocomotiveModelUseCase usecase;

	AddLocomotiveModelRequest request = new AddLocomotiveModelRequest()
			.id(locomotiveModelLMSStainierBlack5Id.id())
			.powerType(LocomotivePowerType.ELECTRIC)
			.name(locomotiveModelLMSStainierBlack5Name)
			.gauge(standardGauge.type());

	@Test
	void success() {
		usecase.execute(request);

		assertLocomotiveModel(jpa.getById(locomotiveModelLMSStainierBlack5Id))
				.isEqualTo(locomotiveModelLMSStainierBlack5());
	}

	@Test
	void throwAlreadyExists() {
		jpa.save(locomotiveModelLMSStainierBlack5());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(locomotiveModelLMSStainierBlack5Id));
	}
}
