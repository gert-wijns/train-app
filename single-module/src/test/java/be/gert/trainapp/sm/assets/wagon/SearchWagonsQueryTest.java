package be.gert.trainapp.sm.assets.wagon;

import static be.gert.trainapp.api.assets.generated.model.WagonTypeEnum.GONDOLA;
import static be.gert.trainapp.sm.assets._model.WagonDefaults.serialNumber;
import static be.gert.trainapp.sm.assets._model.WagonDefaults.testWagon;
import static be.gert.trainapp.sm.assets._model.WagonDefaults.wagonId;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXs;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXsId;
import static be.gert.trainapp.sm.assets._model.WagonModelDefaults.wagonModelXsName;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.assets.generated.model.SearchWagonsQueryResponseItem;
import be.gert.trainapp.api.assets.generated.model.WagonModelResponse;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets._model.Wagon;
import be.gert.trainapp.sm.assets._repository.WagonJpaRepository;
import be.gert.trainapp.sm.assets._repository.WagonModelJpaRepository;

@ModuleTest
class SearchWagonsQueryTest {
	@Autowired
	WagonModelJpaRepository modelJpa;
	@Autowired
	WagonJpaRepository jpa;
	@Autowired
	SearchWagonsQuery query;

	@Test
	void mapsSelectedResult() {
		// given
		modelJpa.save(wagonModelXs());
		jpa.save(testWagon());

		// when
		var response = query.query(List.of(wagonId.id())).getBody();
		assertThat(response)
				.containsExactly(new SearchWagonsQueryResponseItem()
						.id(wagonId.id())
						.model(new WagonModelResponse()
								.id(wagonModelXsId.id())
								.name(wagonModelXsName)
								.gauge(standardGauge.type())
								.type(GONDOLA))
						.serialNumber(serialNumber.sn())
						.decommissioned(false));
	}


	record FilterInput(List<WagonId> wagonIds) {}
	record ExpectedOutput(List<WagonId> wagonIds) {}

	static Wagon wagon1 = testWagon().toBuilder().id(new WagonId("1")).serialNumber(new SerialNumber("1")).build();
	static Wagon wagon2 = testWagon().toBuilder().id(new WagonId("2")).serialNumber(new SerialNumber("2")).build();
	static Wagon wagon3 = testWagon().toBuilder().id(new WagonId("3")).serialNumber(new SerialNumber("3")).build();

	@ParameterizedTest
	@MethodSource("appliesFilterWhenQueryingInput")
	void appliesFilterWhenQuerying(FilterInput input, ExpectedOutput expected) {
		modelJpa.save(wagonModelXs());
		jpa.save(wagon1.toBuilder().build());
		jpa.save(wagon2.toBuilder().build());
		jpa.save(wagon3.toBuilder().build());

		var response = query.query(input.wagonIds.stream().map(WagonId::id).toList()).getBody();

		assertThat(response).extracting(SearchWagonsQueryResponseItem::getId)
				.containsExactlyElementsOf(expected.wagonIds.stream().map(WagonId::id).toList());
	}

	static List<Arguments> appliesFilterWhenQueryingInput() {
		return List.of(
				arguments(new FilterInput(List.of(wagon1.id(), wagon2.id(), new WagonId("4"))),
						new ExpectedOutput(List.of(wagon1.id(), wagon2.id()))),
				arguments(new FilterInput(List.of(wagon1.id(), wagon2.id(), new WagonId("4"))),
						new ExpectedOutput(List.of(wagon1.id(), wagon2.id())))
		);
	}
}
