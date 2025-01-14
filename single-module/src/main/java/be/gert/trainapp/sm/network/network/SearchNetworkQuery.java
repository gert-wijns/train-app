package be.gert.trainapp.sm.network.network;

import static be.gert.trainapp.sm.network._model.QNetwork.network;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.network.generated.SearchNetworkQueryApi;
import be.gert.trainapp.api.network.generated.model.SearchNetworkQueryResponseItem;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchNetworkQuery implements SearchNetworkQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<SearchNetworkQueryResponseItem> query() {
		var query = queryFactory.from(network)
				.select(network.id.id,
						network.name);

		return query.fetch().stream().map(this::toResponseItem).toList();
	}

	private SearchNetworkQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchNetworkQueryResponseItem()
				.id(tuple.get(network.id.id))
				.name(tuple.get(network.name));
	}
}


