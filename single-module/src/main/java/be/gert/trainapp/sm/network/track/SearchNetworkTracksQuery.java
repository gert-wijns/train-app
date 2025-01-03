package be.gert.trainapp.sm.network.track;

import static be.gert.trainapp.sm.network._mapper.SpeedMapper.toSpeedBody;
import static be.gert.trainapp.sm.network.track.model.QTrack.track;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.network.generated.SearchNetworkTracksQueryApi;
import be.gert.trainapp.api.network.generated.model.SearchNetworkTracksQueryResponseItem;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class SearchNetworkTracksQuery implements SearchNetworkTracksQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchNetworkTracksQueryResponseItem>> query() {
		var query = queryFactory.from(track)
				.select(track.id.from.id,
						track.id.to.id,
						track.electrified,
						track.gauge.type,
						track.slope,
						track.speedLimit);

		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchNetworkTracksQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchNetworkTracksQueryResponseItem()
				.fromNodeId(tuple.get(track.id.from.id))
				.toNodeId(tuple.get(track.id.to.id))
				.electrified(tuple.get(track.electrified))
				.gauge(tuple.get(track.gauge.type))
				.slope(tuple.get(track.slope))
				.speedLimit(toSpeedBody(tuple.get(track.speedLimit)));
	}
}

