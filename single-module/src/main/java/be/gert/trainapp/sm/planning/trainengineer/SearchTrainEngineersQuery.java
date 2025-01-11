package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.planning._model.QTrainEngineer.trainEngineer;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.planning.generated.SearchTrainEngineersQueryApi;
import be.gert.trainapp.api.planning.generated.model.SearchTrainEngineersResponseItem;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class SearchTrainEngineersQuery implements SearchTrainEngineersQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchTrainEngineersResponseItem>> query() {
		var query = queryFactory.from(trainEngineer)
				.select(trainEngineer.id.id,
						trainEngineer.active);
		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchTrainEngineersResponseItem toResponseItem(Tuple tuple) {
		return new SearchTrainEngineersResponseItem()
				.id(tuple.get(trainEngineer.id.id))
				.active(tuple.get(trainEngineer.active));
	}
}
