package be.gert.trainapp.sm.assets.wagonmodel;

import static be.gert.trainapp.sm.assets._model.QWagonModel.wagonModel;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchWagonModelsQueryApi;
import be.gert.trainapp.api.assets.generated.model.SearchWagonModelsQueryResponseItem;
import be.gert.trainapp.api.assets.generated.model.WagonTypeEnum;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class SearchWagonModelsQuery implements SearchWagonModelsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchWagonModelsQueryResponseItem>> query() {
		var query = queryFactory.from(wagonModel)
				.select(wagonModel.id.id,
						wagonModel.name,
						wagonModel.gauge.type,
						wagonModel.type)
				.orderBy(wagonModel.name.asc());
		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchWagonModelsQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchWagonModelsQueryResponseItem()
				.id(tuple.get(wagonModel.id.id))
				.name(tuple.get(wagonModel.name))
				.gauge(tuple.get(wagonModel.gauge.type))
				.type(WagonTypeEnum.fromValue(tuple.get(wagonModel.type).name()));
	}
}
