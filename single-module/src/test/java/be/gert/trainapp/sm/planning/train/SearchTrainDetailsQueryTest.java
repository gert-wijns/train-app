package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.emptyOrientExpress;
import static be.gert.trainapp.sm.planning._model.LocomotiveDefaults.locomotiveOrientExpressId;
import static be.gert.trainapp.sm.planning._model.WagonDefaults.orientExpressFirstCoach;
import static be.gert.trainapp.sm.planning._model.TrainLocomotiveDefaults.orientExpressLocomotive;
import static be.gert.trainapp.sm.planning._model.WagonDefaults.trainOrientExpressFirstCoachId;
import static be.gert.trainapp.sm.planning._model.TrainDefaults.trainOrientExpressId;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.SearchTrainDetailsQueryResponse;
import be.gert.trainapp.api.planning.generated.model.SearchTrainDetailsWagonQueryResponse;
import be.gert.trainapp.api.planning.generated.model.TrainLocomotiveResponse;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.planning._repository.TrainJpaRepository;

@ModuleTest
class SearchTrainDetailsQueryTest {
	@Autowired
	TrainJpaRepository jpa;
	@Autowired
	SearchTrainDetailsQuery query;

	@Test
	void success() {
		// given
		jpa.save(emptyOrientExpress()
				.attachWagon(orientExpressFirstCoach()));

		// when
		var response = query.query(trainOrientExpressId.id());

		// then
		assertValid(response);
		assertThat(response.getBody()).isEqualTo(
				new SearchTrainDetailsQueryResponse()
						.id(trainOrientExpressId.id())
						.gauge(standardGauge.type())
						.containsDecommissioned(false)
						.locomotive(new TrainLocomotiveResponse()
								.id(locomotiveOrientExpressId.id())
								.serialNumber(orientExpressLocomotive.serialNumber().sn())
								.decommissioned(orientExpressLocomotive.decommissioned()))
						.wagons(List.of(new SearchTrainDetailsWagonQueryResponse()
								.id(trainOrientExpressFirstCoachId.id())
								.serialNumber(orientExpressFirstCoach().serialNumber().sn())
								.decommissioned(false)))
		);
	}
}
