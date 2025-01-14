package be.gert.trainapp.sm.network.track;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationBrusselsId;
import static be.gert.trainapp.sm.network._model.TrackDefaults.trackAntwerpBrussels;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.SearchNetworkTracksQueryResponseItem;
import be.gert.trainapp.api.network.generated.model.SpeedBody;
import be.gert.trainapp.api.network.generated.model.SpeedBody.MeasurementEnum;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.network._model.Track;
import be.gert.trainapp.sm.network._repository.TrackJpaRepository;

@ModuleTest
class SearchNetworkTracksQueryTest {
	@Autowired
	TrackJpaRepository jpa;
	@Autowired
	SearchNetworkTracksQuery query;

	@Test
	void selectsWhenQuerying() {
		Track track = jpa.save(trackAntwerpBrussels());

		var result = query.query();

		assertValid(result);
		assertThat(result).containsExactly(new SearchNetworkTracksQueryResponseItem()
				.fromNodeId(stationAntwerpId.id())
				.toNodeId(stationBrusselsId.id())
				.electrified(track.electrified())
				.gauge(track.gauge().type())
				.slope(track.slope())
				.speedLimit(new SpeedBody()
						.speed(track.speedLimit().speed())
						.measurement(MeasurementEnum.KPH)));
	}

}
