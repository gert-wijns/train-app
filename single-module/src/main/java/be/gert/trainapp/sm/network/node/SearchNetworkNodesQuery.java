package be.gert.trainapp.sm.network.node;

import static be.gert.trainapp.sm.network._mapper.GeoPositionMapper.toGeoPositionBody;
import static be.gert.trainapp.sm.network.node.model.QNode.node;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.network.generated.SearchNetworkNodesQueryApi;
import be.gert.trainapp.api.network.generated.model.SearchNetworkNodesQueryResponseItem;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class SearchNetworkNodesQuery implements SearchNetworkNodesQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchNetworkNodesQueryResponseItem>> query() {
		var query = queryFactory.from(node)
				.select(node.id.id,
						node.name,
						node.geoPosition);

		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchNetworkNodesQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchNetworkNodesQueryResponseItem()
				.id(tuple.get(node.id.id))
				.name(tuple.get(node.name))
				.geoPosition(toGeoPositionBody(tuple.get(node.geoPosition)));
	}
}


