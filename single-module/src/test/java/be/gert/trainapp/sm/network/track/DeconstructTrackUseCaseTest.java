package be.gert.trainapp.sm.network.track;

import static be.gert.trainapp.sm.network.given.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network.given.NodeDefaults.stationBrusselsId;
import static be.gert.trainapp.sm.network.given.TrackDefaults.trackAntwerpBrussels;
import static be.gert.trainapp.sm.network.given.TrackDefaults.trackAntwerpBrusselsId;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.DecommissionTrackRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;
import be.gert.trainapp.sm.network.track.model.TrackExceptions;

@ModuleTest
class DecommissionTrackUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	DecommissionTrackUseCase usecase;

	DecommissionTrackRequest request = new DecommissionTrackRequest()
			.fromNodeId(stationAntwerpId.id())
			.toNodeId(stationBrusselsId.id());

	@Test
	void success() {
		testEntities.save(trackAntwerpBrussels());

		// when
		usecase.execute(request);

		// then
		testEntities.assertState(trackAntwerpBrussels().toBuilder()
				.decomissioned(true)
				.build());
	}

	@Test
	void throwsNotFound() {
		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(TrackExceptions.notFound(trackAntwerpBrusselsId));
	}

}
