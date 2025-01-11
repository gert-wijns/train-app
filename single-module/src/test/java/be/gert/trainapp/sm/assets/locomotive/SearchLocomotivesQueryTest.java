package be.gert.trainapp.sm.assets.locomotive;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.locomotive1937Id;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.locomotiveStainier;
import static be.gert.trainapp.sm.assets._model.LocomotiveDefaults.serialNumberStainier;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5Id;
import static be.gert.trainapp.sm.assets._model.LocomotiveModelDefaults.locomotiveModelLMSStainierBlack5Name;
import static be.gert.trainapp.sm.network._model.TrackDefaults.standardGauge;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.assets.generated.model.LocomotiveModelResponse;
import be.gert.trainapp.api.assets.generated.model.LocomotivePowerType;
import be.gert.trainapp.api.assets.generated.model.SearchLocomotivesQueryResponseItem;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets._model.Locomotive;
import be.gert.trainapp.sm.assets._repository.LocomotiveJpaRepository;
import be.gert.trainapp.sm.assets._repository.LocomotiveModelJpaRepository;

@ModuleTest
class SearchLocomotivesQueryTest {
	@Autowired
	LocomotiveModelJpaRepository modelJpa;
	@Autowired
	LocomotiveJpaRepository jpa;
	@Autowired
	SearchLocomotivesQuery query;

	@Test
	void mapsSelectedResult() {
		// given
		modelJpa.save(locomotiveModelLMSStainierBlack5());
		jpa.save(locomotiveStainier());

		// when
		var response = query.query(List.of(locomotive1937Id.id())).getBody();
		assertValid(response);
		assertThat(response)
				.containsExactly(new SearchLocomotivesQueryResponseItem()
						.id(locomotive1937Id.id())
						.name("Stainier")
						.model(new LocomotiveModelResponse()
								.id(locomotiveModelLMSStainierBlack5Id.id())
								.name(locomotiveModelLMSStainierBlack5Name)
								.powerType(LocomotivePowerType.ELECTRIC)
								.gauge(standardGauge.type()))
						.serialNumber(serialNumberStainier.sn())
						.decommissioned(false));
	}

	record FilterInput(List<LocomotiveId> locomotiveIds) {}
	record ExpectedOutput(List<LocomotiveId> locomotiveIds) {}

	static Locomotive locomotive1 = locomotiveStainier().toBuilder().id(new LocomotiveId("1")).serialNumber(new SerialNumber("1")).build();
	static Locomotive locomotive2 = locomotiveStainier().toBuilder().id(new LocomotiveId("2")).serialNumber(new SerialNumber("2")).build();
	static Locomotive locomotive3 = locomotiveStainier().toBuilder().id(new LocomotiveId("3")).serialNumber(new SerialNumber("3")).build();

	@ParameterizedTest
	@MethodSource("appliesFilterWhenQueryingInput")
	void appliesFilterWhenQuerying(FilterInput input, ExpectedOutput expected) {
		modelJpa.save(locomotiveModelLMSStainierBlack5());
		jpa.save(locomotive1.toBuilder().build());
		jpa.save(locomotive2.toBuilder().build());
		jpa.save(locomotive3.toBuilder().build());

		var response = query.query(input.locomotiveIds.stream().map(LocomotiveId::id).toList()).getBody();

		assertThat(response).extracting(SearchLocomotivesQueryResponseItem::getId)
				.containsExactlyElementsOf(expected.locomotiveIds.stream().map(LocomotiveId::id).toList());
	}

	static List<Arguments> appliesFilterWhenQueryingInput() {
		return List.of(
				arguments(new FilterInput(List.of(locomotive1.id(), locomotive2.id(), new LocomotiveId("4"))),
						new ExpectedOutput(List.of(locomotive1.id(), locomotive2.id()))),
				arguments(new FilterInput(List.of(locomotive1.id(), locomotive2.id(), new LocomotiveId("4"))),
						new ExpectedOutput(List.of(locomotive1.id(), locomotive2.id())))
		);
	}
}
