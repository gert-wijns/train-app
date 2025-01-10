package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.QTrain.train;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.planning.generated.SearchTrainsQueryApi;
import be.gert.trainapp.api.planning.generated.model.SearchTrainsQueryResponseItem;
import be.gert.trainapp.api.planning.generated.model.TrainLocomotiveResponse;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class SearchTrainsQuery implements SearchTrainsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchTrainsQueryResponseItem>> query() {
		var query = queryFactory.from(train)
				.select(train.id.id,
						train.gauge.type,
						train.containsDecommissioned,
						train.locomotive.id.id,
						train.locomotive.decommissioned,
						train.locomotive.serialNumber.sn);

		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchTrainsQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchTrainsQueryResponseItem()
				.id(tuple.get(train.id.id))
				.gauge(tuple.get(train.gauge.type))
				.containsDecommissioned(tuple.get(train.containsDecommissioned))
				.locomotive(new TrainLocomotiveResponse()
						.id(tuple.get(train.locomotive.id.id))
						.decommissioned(tuple.get(train.locomotive.decommissioned))
						.serialNumber(tuple.get(train.locomotive.serialNumber.sn)));
	}
}
