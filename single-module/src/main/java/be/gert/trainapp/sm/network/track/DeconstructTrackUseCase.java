package be.gert.trainapp.sm.network.track;

import static be.gert.trainapp.sm.network.track.model.TrackExceptions.notFound;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.network.generated.DeconstructTrackUseCaseApi;
import be.gert.trainapp.api.network.generated.model.DeconstructTrackRequest;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network.TrackId;
import be.gert.trainapp.sm.network.track.jpa.TrackJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class DeconstructTrackUseCase implements DeconstructTrackUseCaseApi {
	private final TrackJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(DeconstructTrackRequest request) {
		TrackId id = new TrackId(
				new NodeId(request.getFromNodeId()),
				new NodeId(request.getToNodeId()));
		var track = jpa.findById(id).orElseThrow(() -> notFound(id));

		jpa.delete(track);

		return noContent().build();
	}
}
