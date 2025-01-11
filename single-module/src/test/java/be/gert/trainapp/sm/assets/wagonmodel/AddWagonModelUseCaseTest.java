package be.gert.trainapp.sm.assets.wagonmodel;

import static be.gert.trainapp.api.assets.generated.model.WagonTypeEnum.GONDOLA;
import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXs;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXsId;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXsName;
import static be.gert.trainapp.sm.assets.wagonmodel.AddWagonModelUseCase.alreadyExists;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.assets.generated.model.AddWagonModelRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository;

@ModuleTest
class AddWagonModelUseCaseTest {
	@Autowired
	WagonModelJpaRepository jpa;
	@Autowired
	AddWagonModelUseCase usecase;

	AddWagonModelRequest request = new AddWagonModelRequest()
			.id(wagonModelXsId.id())
			.name(wagonModelXsName)
			.gauge(standardGauge.type())
			.type(GONDOLA);

	@Test
	void success() {
		usecase.execute(request);

		assertEntity(jpa.getById(wagonModelXsId))
				.isEqualTo(wagonModelXs());
	}

	@Test
	void throwAlreadyExists() {
		jpa.save(wagonModelXs());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(wagonModelXsId));
	}
}
