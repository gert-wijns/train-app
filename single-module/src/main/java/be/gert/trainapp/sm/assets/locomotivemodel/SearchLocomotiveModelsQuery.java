package be.gert.trainapp.sm.assets.locomotivemodel;

import static be.gert.trainapp.sm.assets._model.QLocomotiveModel.locomotiveModel;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchLocomotiveModelsQueryApi;
import be.gert.trainapp.api.assets.generated.model.LocomotivePowerType;
import be.gert.trainapp.api.assets.generated.model.SearchLocomotiveModelsQueryResponseItem;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchLocomotiveModelsQuery implements SearchLocomotiveModelsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<SearchLocomotiveModelsQueryResponseItem> query() {
		var query = queryFactory.from(locomotiveModel)
				.select(locomotiveModel.id.id,
						locomotiveModel.name,
						locomotiveModel.gauge.type,
						locomotiveModel.powerType)
				.orderBy(locomotiveModel.name.asc());
		return query.fetch().stream().map(this::toResponseItem).toList();
	}

	private SearchLocomotiveModelsQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchLocomotiveModelsQueryResponseItem()
				.id(tuple.get(locomotiveModel.id.id))
				.name(tuple.get(locomotiveModel.name))
				.gauge(tuple.get(locomotiveModel.gauge.type))
				.powerType(LocomotivePowerType.valueOf(tuple.get(locomotiveModel.powerType).name()));
	}
}
