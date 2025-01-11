package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._mapper.GeoPositionMapper.toGeoPositionBody;
import static be.gert.trainapp.sm.network._model.QNetwork.network;
import static be.gert.trainapp.sm.network._model.QNode.node;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.network.generated.SearchNetworkNodesQueryApi;
import be.gert.trainapp.api.network.generated.model.SearchNetworkNodesQueryResponseItem;
import be.gert.trainapp.api.network.generated.model.SearchNetworkNodesQueryResponseItemNetwork;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class SearchNetworkNodesQuery implements SearchNetworkNodesQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	@Valid
	public ResponseEntity<List<SearchNetworkNodesQueryResponseItem>> query() {
		var query = queryFactory.from(node)
				.join(network).on(network.id.eq(node.networkId))
				.select(node.id.id,
						node.name,
						node.geoPosition,
						network.id.id,
						network.name);

		return ok(query.fetch().stream().map(this::toResponseItem).toList());
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


