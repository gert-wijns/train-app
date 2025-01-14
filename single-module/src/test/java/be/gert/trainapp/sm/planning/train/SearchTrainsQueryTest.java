package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.LocomotiveDefaults.locomotiveOrientExpressId;
import static be.gert.trainapp.sm.planning._model.TrainLocomotiveDefaults.orientExpressLocomotive;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.SearchTrainsQueryResponseItem;
import be.gert.trainapp.api.planning.generated.model.TrainLocomotiveResponse;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;

@ModuleTest
class SearchTrainsQueryTest {
	@Autowired
	TrainJpaRepository jpa;
	@Autowired
	SearchTrainsQuery query;

	@Test
	void success() {
		// given
		jpa.save(emptyOrientExpress());

		// when
		var response = query.query();

		// then
		assertValid(response);
		assertThat(response).containsExactly(
				new SearchTrainsQueryResponseItem()
						.id(trainOrientExpressId.id())
						.gauge(standardGauge.type())
						.containsDecommissioned(false)
						.locomotive(new TrainLocomotiveResponse()
								.id(locomotiveOrientExpressId.id())
								.serialNumber(orientExpressLocomotive.serialNumber().sn())
								.decommissioned(orientExpressLocomotive.decommissioned()))
		);
	}

}
