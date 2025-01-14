package be.gert.trainapp.sm.assets.wagonmodel;

import static be.gert.trainapp.api.assets.generated.model.WagonTypeEnum.GONDOLA;
import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXs;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXsId;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXsName;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.assets.generated.model.SearchWagonModelsQueryResponseItem;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository;

@ModuleTest
class SearchWagonModelsQueryTest {
	@Autowired
	WagonModelJpaRepository modelJpa;
	@Autowired
	SearchWagonModelsQuery query;

	@Test
	void mapsSelectedResult() {
		// given
		modelJpa.save(wagonModelXs());

		// when
		var response = query.query();
		assertValid(response);
		assertThat(response)
				.containsExactly(new SearchWagonModelsQueryResponseItem()
						.id(wagonModelXsId.id())
						.name(wagonModelXsName)
						.type(GONDOLA)
						.gauge(standardGauge.type()));
	}

}
