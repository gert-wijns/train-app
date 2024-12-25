package be.gert.trainapp.sm.assets.asset;

import static be.gert.trainapp.sm.assets.asset.model.QAsset.asset;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchAssetsQueryApi;
import be.gert.trainapp.api.assets.generated.model.AssetType;
import be.gert.trainapp.api.assets.generated.model.SearchAssetsQueryResponseItem;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class SearchAssetsQuery implements SearchAssetsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchAssetsQueryResponseItem>> query() {
		var query = queryFactory.from(asset)
				.select(asset.id, asset.type, asset.name, asset.subtype);

		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchAssetsQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchAssetsQueryResponseItem()
				.id(tuple.get(asset.id).id())
				.type(AssetType.fromValue(tuple.get(asset.type).name()))
				.subtype(tuple.get(asset.subtype))
				.name(tuple.get(asset.name));
	}
}
