package be.gert.trainapp.sm.planning._adapter;

import static be.gert.trainapp.sm.planning._model.LocomotiveDefaults.locomotiveOrientExpress;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.gert.trainapp.api.assets.generated.SearchLocomotivesQueryApi;
import be.gert.trainapp.api.assets.generated.model.LocomotiveModelResponse;
import be.gert.trainapp.api.assets.generated.model.LocomotivePowerType;
import be.gert.trainapp.api.assets.generated.model.SearchLocomotivesQueryResponseItem;

@ExtendWith(MockitoExtension.class)
public class SearchLocomotiveImplTest {
	@Mock
	SearchLocomotivesQueryApi searchLocomotivesQueryApi;
	@InjectMocks
	SearchLocomotiveImpl searchLocomotive;

	SearchLocomotivesQueryResponseItem item = new SearchLocomotivesQueryResponseItem()
			.id(locomotiveOrientExpress.id().id())
			.name("Locomotive-orientExpress")
			.decommissioned(false)
			.serialNumber(locomotiveOrientExpress.serialNumber().sn())
			.model(new LocomotiveModelResponse()
					.id("Locomotive-orientExpress-ModelId")
					.gauge(locomotiveOrientExpress.gauge().type())
					.powerType(LocomotivePowerType.ELECTRIC));

	@Test
	void testSearchLocomotive() {
		when(searchLocomotivesQueryApi.query(List.of(locomotiveOrientExpress.id().id())))
				.thenReturn(List.of(item));

		var actual = searchLocomotive.getById(locomotiveOrientExpress.id());

		assertThat(actual).isEqualTo(locomotiveOrientExpress);
	}
}
