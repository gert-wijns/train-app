package be.gert.trainapp.sm.network.network;

import static be.gert.trainapp.sm.network._model.QNetwork.network;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.network.generated.SearchNetworkQueryApi;
import be.gert.trainapp.api.network.generated.model.SearchNetworkQueryResponseItem;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class SearchNetworkQuery implements SearchNetworkQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchNetworkQueryResponseItem>> query() {
		var query = queryFactory.from(network)
				.select(network.id.id,
						network.name);

		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchNetworkQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchNetworkQueryResponseItem()
				.id(tuple.get(network.id.id))
				.name(tuple.get(network.name));
	}
}


