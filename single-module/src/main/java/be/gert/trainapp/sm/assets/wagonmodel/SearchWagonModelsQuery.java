package be.gert.trainapp.sm.assets.wagonmodel;

import static be.gert.trainapp.sm.assets._model.QWagonModel.wagonModel;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchWagonModelsQueryApi;
import be.gert.trainapp.api.assets.generated.model.SearchWagonModelsQueryResponseItem;
import be.gert.trainapp.api.assets.generated.model.WagonTypeEnum;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchWagonModelsQuery implements SearchWagonModelsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<SearchWagonModelsQueryResponseItem> query() {
		var query = queryFactory.from(wagonModel)
				.select(wagonModel.id.id,
						wagonModel.name,
						wagonModel.gauge.type,
						wagonModel.type)
				.orderBy(wagonModel.name.asc());
		return query.fetch().stream().map(this::toResponseItem).toList();
	}

	private SearchWagonModelsQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchWagonModelsQueryResponseItem()
				.id(tuple.get(wagonModel.id.id))
				.name(tuple.get(wagonModel.name))
				.gauge(tuple.get(wagonModel.gauge.type))
				.type(WagonTypeEnum.fromValue(tuple.get(wagonModel.type).name()));
	}
}
