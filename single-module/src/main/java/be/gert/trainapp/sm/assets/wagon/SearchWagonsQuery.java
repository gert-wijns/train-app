package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.sm.assets._model.QWagon.wagon;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.assets.generated.SearchWagonsQueryApi;
import be.gert.trainapp.api.assets.generated.model.SearchWagonsQueryResponseItem;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class SearchWagonsQuery implements SearchWagonsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchWagonsQueryResponseItem>> query(List<String> wagonId) {
		var query = queryFactory.from(wagon)
				.select(wagon.id.id,
						wagon.modelId.id,
						wagon.serialNumber.sn,
						wagon.gauge.type)
				.orderBy(wagon.serialNumber.sn.asc());
		if (isNotEmpty(wagonId)) {
			query.where(wagon.id.id.in(wagonId));
		}
		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchWagonsQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchWagonsQueryResponseItem()
				.id(tuple.get(wagon.id.id))
				.modelTypeId(tuple.get(wagon.modelId.id))
				.serialNumber(tuple.get(wagon.serialNumber.sn))
				.gauge(tuple.get(wagon.gauge.type));
	}
}
