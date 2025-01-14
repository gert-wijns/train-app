package be.gert.trainapp.sm.assets.locomotive;

import static be.gert.trainapp.sm.assets._model.QLocomotive.locomotive;
import static be.gert.trainapp.sm.assets._model.QLocomotiveModel.locomotiveModel;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchLocomotivesQueryApi;
import be.gert.trainapp.api.assets.generated.model.LocomotiveModelResponse;
import be.gert.trainapp.api.assets.generated.model.LocomotivePowerType;
import be.gert.trainapp.api.assets.generated.model.SearchLocomotivesQueryResponseItem;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchLocomotivesQuery implements SearchLocomotivesQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<SearchLocomotivesQueryResponseItem> query(List<String> locomotiveId) {
		var query = queryFactory.from(locomotive)
				.join(locomotiveModel).on(locomotiveModel.id.eq(locomotive.modelId))
				.select(
						locomotive.id.id,
						locomotive.name,
						locomotive.serialNumber.sn,
						locomotive.decommissioned,
						locomotiveModel.id.id,
						locomotiveModel.name,
						locomotiveModel.gauge.type,
						locomotiveModel.powerType)
				.orderBy(locomotive.serialNumber.sn.asc());
		if (isNotEmpty(locomotiveId)) {
			query.where(locomotive.id.id.in(locomotiveId));
		}
		return query.fetch().stream().map(this::toResponseItem).toList();
	}

	private SearchLocomotivesQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchLocomotivesQueryResponseItem()
				.id(tuple.get(locomotive.id.id))
				.name(tuple.get(locomotive.name))
				.serialNumber(tuple.get(locomotive.serialNumber.sn))
				.decommissioned(tuple.get(locomotive.decommissioned))
				.model(new LocomotiveModelResponse()
						.id(tuple.get(locomotiveModel.id.id))
						.name(tuple.get(locomotiveModel.name))
						.gauge(tuple.get(locomotiveModel.gauge.type))
						.powerType(LocomotivePowerType.valueOf(tuple.get(locomotiveModel.powerType).name())));
	}
}
