package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.QTrain.train;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.planning.generated.SearchTrainsQueryApi;
import be.gert.trainapp.api.planning.generated.model.SearchTrainsQueryResponseItem;
import be.gert.trainapp.api.planning.generated.model.TrainLocomotiveResponse;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchTrainsQuery implements SearchTrainsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<SearchTrainsQueryResponseItem> query() {
		var query = queryFactory.from(train)
				.select(train.id.id,
						train.gauge.type,
						train.containsDecommissioned,
						train.locomotive.id.id,
						train.locomotive.decommissioned,
						train.locomotive.serialNumber.sn);

		return query.fetch().stream().map(this::toResponseItem).toList();
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
