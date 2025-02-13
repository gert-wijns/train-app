package be.gert.trainapp.sm.planning._adapter;

import static be.gert.trainapp.sm.planning._model.WagonDefaults.orientExpressFirstCoach;
import static be.gert.trainapp.sm.planning._model.WagonDefaults.trainOrientExpressFirstCoachId;
import static be.gert.trainapp.sm.planning._model.WagonDefaults.wagonModelTypeId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.gert.trainapp.api.assets.generated.SearchWagonsQueryApi;
import be.gert.trainapp.api.assets.generated.model.SearchWagonsQueryResponseItem;
import be.gert.trainapp.api.assets.generated.model.WagonModelResponse;

@ExtendWith(MockitoExtension.class)
class SearchWagonUsingAssetsApiTest {
	@Mock
	SearchWagonsQueryApi searchWagonsQueryApi;
	@InjectMocks
	SearchWagonUsingAssetsApi adapter;

	SearchWagonsQueryResponseItem item = new SearchWagonsQueryResponseItem()
			.id(orientExpressFirstCoach.id().id())
			.serialNumber(orientExpressFirstCoach.serialNumber().sn())
			.decommissioned(orientExpressFirstCoach.decommissioned())
			.model(new WagonModelResponse()
					.id(wagonModelTypeId.id())
					.gauge(orientExpressFirstCoach.gauge().type()));

	@Test
	void testSearchLocomotive() {
		when(searchWagonsQueryApi.query(List.of(trainOrientExpressFirstCoachId.id())))
				.thenReturn(List.of(item));

		var actual = adapter.getById(trainOrientExpressFirstCoachId);

		assertThat(actual).isEqualTo(orientExpressFirstCoach);
	}
}
