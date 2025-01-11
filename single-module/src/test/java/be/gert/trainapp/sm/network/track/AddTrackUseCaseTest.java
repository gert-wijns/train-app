package be.gert.trainapp.sm.network.track;

import static be.gert.trainapp.sm.EntityAssertionDefaults.assertEntity;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network._model.NodeDefaults.stationBrusselsId;
import static be.gert.trainapp.sm.network._model.TrackDefaults.trackAntwerpBrussels;
import static be.gert.trainapp.sm.network._model.TrackDefaults.trackAntwerpBrusselsId;
import static be.gert.trainapp.sm.network.track.AddTrackUseCase.alreadyExists;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.AddTrackRequest;
import be.gert.trainapp.api.network.generated.model.SpeedBody;
import be.gert.trainapp.api.network.generated.model.SpeedBody.MeasurementEnum;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.network._repository.TrackJpaRepository;

@ModuleTest
class AddTrackUseCaseTest {
	@Autowired
	TrackJpaRepository jpa;
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
		assertEntity(jpa.getById(trackAntwerpBrusselsId))
				.isEqualTo(trackAntwerpBrussels());
	}

	@Test
	void throwsAlreadyExists() {
		// given
		jpa.save(trackAntwerpBrussels());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(trackAntwerpBrusselsId));
	}

}
