package be.gert.trainapp.sm.planning.train;

import static be.gert.trainapp.sm.planning._model.QTrain.train;
import static be.gert.trainapp.sm.planning._model.QTrainWagon.trainWagon;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.planning.generated.SearchTrainDetailsQueryApi;
import be.gert.trainapp.api.planning.generated.model.SearchTrainDetailsQueryResponse;
import be.gert.trainapp.api.planning.generated.model.SearchTrainDetailsQueryResponseTrainEngineer;
import be.gert.trainapp.api.planning.generated.model.SearchTrainDetailsWagonQueryResponse;
import be.gert.trainapp.api.planning.generated.model.TrainLocomotiveResponse;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class SearchTrainDetailsQuery implements SearchTrainDetailsQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<SearchTrainDetailsQueryResponse> query(String trainId) {
		var trainTuple = fetchTrain(trainId);
		var wagons = fetchWagons(trainId);
		return ok(toResponseItem(trainTuple, wagons));
	}

	private List<SearchTrainDetailsWagonQueryResponse> fetchWagons(String trainId) {
		var wagonsQuery = queryFactory.from(trainWagon)
				.select(trainWagon.id.id,
						trainWagon.serialNumber.sn,
						trainWagon.decommissioned)
				.where(trainWagon.train.id.id.eq(trainId));
		return wagonsQuery.fetch().stream().map(this::toResponse).toList();
	}

	private Tuple fetchTrain(String trainId) {
		var trainQuery = queryFactory.from(train)
				.select(train.id.id,
						train.gauge.type,
						train.locomotive.id.id,
						train.locomotive.decommissioned,
						train.locomotive.serialNumber.sn,
						train.containsDecommissioned,
						train.trainEngineer.id)
				.where(train.id.id.eq(trainId));
		return trainQuery.fetch().getFirst();
	}

	private SearchTrainDetailsWagonQueryResponse toResponse(Tuple tuple) {
		return new SearchTrainDetailsWagonQueryResponse()
				.id(tuple.get(trainWagon.id.id))
				.decommissioned(tuple.get(trainWagon.decommissioned))
				.serialNumber(tuple.get(trainWagon.serialNumber.sn));
	}

	private SearchTrainDetailsQueryResponse toResponseItem(Tuple tuple,
	                                                       List<SearchTrainDetailsWagonQueryResponse> wagons) {
		SearchTrainDetailsQueryResponseTrainEngineer trainEngineer = null;
		if (tuple.get(train.trainEngineer.id) != null) {
			trainEngineer = new SearchTrainDetailsQueryResponseTrainEngineer()
					.id(tuple.get(train.trainEngineer.id));
		}
		return new SearchTrainDetailsQueryResponse()
				.id(tuple.get(train.id.id))
				.gauge(tuple.get(train.gauge.type))
				.locomotive(new TrainLocomotiveResponse()
						.id(tuple.get(train.locomotive.id.id))
						.decommissioned(tuple.get(train.locomotive.decommissioned))
						.serialNumber(tuple.get(train.locomotive.serialNumber.sn)))
				.wagons(wagons)
				.trainEngineer(trainEngineer)
				.containsDecommissioned(tuple.get(train.containsDecommissioned));
	}
}
