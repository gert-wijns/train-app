package be.gert.trainapp.sm.assets.locomotivemodel;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5Id;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5Name;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.assets.generated.model.LocomotivePowerType;
import be.gert.trainapp.api.assets.generated.model.SearchLocomotiveModelsQueryResponseItem;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository;

@ModuleTest
class SearchLocomotiveModelsQueryTest {
	@Autowired
	LocomotiveModelJpaRepository modelJpa;
	@Autowired
	SearchLocomotiveModelsQuery query;

	@Test
	void mapsSelectedResult() {
		// given
		modelJpa.save(locomotiveModelLMSStainierBlack5());

		// when
		var response = query.query();
		assertValid(response);
		assertThat(response)
				.containsExactly(new SearchLocomotiveModelsQueryResponseItem()
						.id(locomotiveModelLMSStainierBlack5Id.id())
						.name(locomotiveModelLMSStainierBlack5Name)
						.powerType(LocomotivePowerType.ELECTRIC)
						.gauge(standardGauge.type()));
	}

}
