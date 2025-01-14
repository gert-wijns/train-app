package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.planning._model.QTrainEngineer.trainEngineer;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.planning.generated.SearchTrainEngineersQueryApi;
import be.gert.trainapp.api.planning.generated.model.SearchTrainEngineersResponseItem;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchTrainEngineersQuery implements SearchTrainEngineersQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<SearchTrainEngineersResponseItem> query() {
		var query = queryFactory.from(trainEngineer)
				.select(trainEngineer.id.id,
						trainEngineer.active);
		return query.fetch().stream().map(this::toResponseItem).toList();
	}

	private SearchTrainEngineersResponseItem toResponseItem(Tuple tuple) {
		return new SearchTrainEngineersResponseItem()
				.id(tuple.get(trainEngineer.id.id))
				.active(tuple.get(trainEngineer.active));
	}
}
