package be.gert.trainapp.sm.network.track;

import static be.gert.trainapp.sm.network._model.TrackExceptions.notFound;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.network.generated.DecommissionTrackUseCaseApi;
import be.gert.trainapp.api.network.generated.model.DecommissionTrackRequest;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network.TrackId;
import be.gert.trainapp.sm.network._repository.TrackJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class DecommissionTrackUseCase implements DecommissionTrackUseCaseApi {
	private final TrackJpaRepository jpa;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(DecommissionTrackRequest request) {
		TrackId id = new TrackId(
				new NodeId(request.getFromNodeId()),
				new NodeId(request.getToNodeId()));
		var track = jpa.findById(id).orElseThrow(() -> notFound(id));

		jpa.save(track.decomission());

		return noContent().build();
	}
}
