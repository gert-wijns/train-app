package be.gert.trainapp.sm.network.track;

import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationBrusselsId;
import static be.gert.trainapp.sm.network._model.TrackDefaults.trackAntwerpBrussels;
import static be.gert.trainapp.sm.network._model.TrackDefaults.trackAntwerpBrusselsId;
import static be.gert.trainapp.sm.network._model.TrackExceptions.alreadyExists;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.AddTrackRequest;
import be.gert.trainapp.api.network.generated.model.SpeedBody;
import be.gert.trainapp.api.network.generated.model.SpeedBody.MeasurementEnum;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;

@ModuleTest
class AddTrackUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	AddTrackUseCase usecase;

	AddTrackRequest request = new AddTrackRequest()
			.fromNodeId(stationAntwerpId.id())
			.toNodeId(stationBrusselsId.id())
			.electrified(trackAntwerpBrussels().electrified())
			.slope(trackAntwerpBrussels().slope())
			.speedLimit(new SpeedBody()
					.speed(trackAntwerpBrussels().speedLimit().speed())
					.measurement(MeasurementEnum.KPH))
			.gauge(trackAntwerpBrussels().gauge().type());

	@Test
	void success() {
		// when
		usecase.execute(request);

		// then
		testEntities.assertState(trackAntwerpBrussels());
	}

	@Test
	void throwsAlreadyExists() {
		// given
		testEntities.save(trackAntwerpBrussels());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(trackAntwerpBrusselsId));
	}

}
