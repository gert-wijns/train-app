package be.gert.trainapp.sm.assets.locomotivemodel;

import static be.gert.trainapp.sm.assets._model.QLocomotiveModel.locomotiveModel;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchLocomotiveModelsQueryApi;
import be.gert.trainapp.api.assets.generated.model.LocomotivePowerType;
import be.gert.trainapp.api.assets.generated.model.SearchLocomotiveModelsQueryResponseItem;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class SearchLocomotiveModelsQuery implements SearchLocomotiveModelsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchLocomotiveModelsQueryResponseItem>> query() {
		var query = queryFactory.from(locomotiveModel)
				.select(locomotiveModel.id.id,
						locomotiveModel.name,
						locomotiveModel.gauge.type,
						locomotiveModel.powerType)
				.orderBy(locomotiveModel.name.asc());
		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchLocomotiveModelsQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchLocomotiveModelsQueryResponseItem()
				.id(tuple.get(locomotiveModel.id.id))
				.name(tuple.get(locomotiveModel.name))
				.gauge(tuple.get(locomotiveModel.gauge.type))
				.powerType(LocomotivePowerType.valueOf(tuple.get(locomotiveModel.powerType).name()));
	}
}
