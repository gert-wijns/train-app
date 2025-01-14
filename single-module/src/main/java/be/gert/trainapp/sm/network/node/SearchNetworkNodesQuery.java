package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._mapper.GeoPositionMapper.toGeoPositionBody;
import static be.gert.trainapp.sm.network._model.QNetwork.network;
import static be.gert.trainapp.sm.network._model.QNode.node;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.network.generated.SearchNetworkNodesQueryApi;
import be.gert.trainapp.api.network.generated.model.SearchNetworkNodesQueryResponseItem;
import be.gert.trainapp.api.network.generated.model.SearchNetworkNodesQueryResponseItemNetwork;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchNetworkNodesQuery implements SearchNetworkNodesQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	@Valid
	public List<SearchNetworkNodesQueryResponseItem> query() {
		var query = queryFactory.from(node)
				.join(network).on(network.id.eq(node.networkId))
				.select(node.id.id,
						node.name,
						node.geoPosition,
						network.id.id,
						network.name);

		return query.fetch().stream().map(this::toResponseItem).toList();
	}

	private SearchNetworkNodesQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchNetworkNodesQueryResponseItem()
				.id(tuple.get(node.id.id))
				.name(tuple.get(node.name))
				.network(new SearchNetworkNodesQueryResponseItemNetwork()
						.id(tuple.get(network.id.id))
						.name(tuple.get(network.name)))
				.geoPosition(toGeoPositionBody(tuple.get(node.geoPosition)));
	}
}


