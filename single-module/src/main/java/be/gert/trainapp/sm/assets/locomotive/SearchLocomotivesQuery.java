package be.gert.trainapp.sm.assets.locomotive;

import static be.gert.trainapp.sm.assets._model.QLocomotive.locomotive;
import static be.gert.trainapp.sm.assets._model.QLocomotiveModel.locomotiveModel;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchLocomotivesQueryApi;
import be.gert.trainapp.api.assets.generated.model.LocomotiveModelResponse;
import be.gert.trainapp.api.assets.generated.model.LocomotivePowerType;
import be.gert.trainapp.api.assets.generated.model.SearchLocomotivesQueryResponseItem;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class SearchLocomotivesQuery implements SearchLocomotivesQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchLocomotivesQueryResponseItem>> query(List<String> locomotiveId) {
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
		return ok(query.fetch().stream().map(this::toResponseItem).toList());
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
