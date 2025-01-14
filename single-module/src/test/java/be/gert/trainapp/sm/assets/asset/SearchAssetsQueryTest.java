package be.gert.trainapp.sm.assets.asset;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.assets.generated.model.SearchAssetsQueryResponseItem;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.assets.AssetId;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets._model.Asset;
import be.gert.trainapp.sm.assets._model.AssetType;
import be.gert.trainapp.sm.assets._repository.AssetJpaRepository;

@ModuleTest
class SearchAssetsQueryTest {
	@Autowired
	AssetJpaRepository jpa;
	@Autowired
	SearchAssetsQuery query;

	@Test
	void selectsWhenQuerying() {
		AssetId id = new AssetId("locomotive-1");
		String name = "locomotive-1-name";
		String subtype = "model-id";
		SerialNumber serialNumber = new SerialNumber("SN-123");
		jpa.save(new Asset(
				id,
				AssetType.LOCOMOTIVE,
				name,
				subtype,
				serialNumber));

		var result = query.query();

		assertValid(result);
		assertThat(result).containsExactly(new SearchAssetsQueryResponseItem()
				.id(id.id())
				.type(be.gert.trainapp.api.assets.generated.model.AssetType.LOCOMOTIVE)
				.name(name)
				.subtype(subtype)
				.serialNumber(serialNumber.sn()));
	}
}
