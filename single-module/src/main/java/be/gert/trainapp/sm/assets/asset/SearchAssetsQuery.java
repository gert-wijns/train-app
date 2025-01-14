package be.gert.trainapp.sm.assets.asset;

import static be.gert.trainapp.sm.assets._model.QAsset.asset;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchAssetsQueryApi;
import be.gert.trainapp.api.assets.generated.model.AssetType;
import be.gert.trainapp.api.assets.generated.model.SearchAssetsQueryResponseItem;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchAssetsQuery implements SearchAssetsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<SearchAssetsQueryResponseItem> query() {
		var query = queryFactory.from(asset)
				.select(asset.id.id,
						asset.type,
						asset.name,
						asset.subtype,
						asset.serialNumber.sn)
				.orderBy(asset.serialNumber.sn.asc());

		return query.fetch().stream().map(this::toResponseItem).toList();
	}

	private SearchAssetsQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchAssetsQueryResponseItem()
				.id(tuple.get(asset.id.id))
				.type(AssetType.fromValue(tuple.get(asset.type).name()))
				.subtype(tuple.get(asset.subtype))
				.name(tuple.get(asset.name))
				.serialNumber(tuple.get(asset.serialNumber.sn));
	}
}
