package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.sm.assets._model.QWagon.wagon;
import static be.gert.trainapp.sm.assets._model.QWagonModel.wagonModel;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchWagonsQueryApi;
import be.gert.trainapp.api.assets.generated.model.SearchWagonsQueryResponseItem;
import be.gert.trainapp.api.assets.generated.model.WagonModelResponse;
import be.gert.trainapp.api.assets.generated.model.WagonTypeEnum;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchWagonsQuery implements SearchWagonsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<SearchWagonsQueryResponseItem> query(List<String> wagonId) {
		var query = queryFactory.from(wagon)
				.join(wagonModel).on(wagonModel.id.eq(wagon.modelId))
				.select(wagon.id.id,
						wagonModel.id.id,
						wagonModel.name,
						wagonModel.gauge.type,
						wagonModel.type,
						wagon.serialNumber.sn,
						wagon.decommissioned)
				.orderBy(wagon.serialNumber.sn.asc());
		if (isNotEmpty(wagonId)) {
			query.where(wagon.id.id.in(wagonId));
		}
		return query.fetch().stream().map(this::toResponseItem).toList();
	}

	private SearchWagonsQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchWagonsQueryResponseItem()
				.id(tuple.get(wagon.id.id))
				.model(new WagonModelResponse()
						.id(tuple.get(wagonModel.id.id))
						.name(tuple.get(wagonModel.name))
						.gauge(tuple.get(wagonModel.gauge.type))
						.type(WagonTypeEnum.fromValue(tuple.get(wagonModel.type).name())))
				.serialNumber(tuple.get(wagon.serialNumber.sn))
				.decommissioned(tuple.get(wagon.decommissioned));
	}
}
