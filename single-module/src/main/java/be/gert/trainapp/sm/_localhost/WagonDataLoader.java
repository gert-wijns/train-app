package be.gert.trainapp.sm._localhost;

import static be.gert.trainapp.sm._localhost.NetworkDataLoader.standardGauge;
import static java.util.stream.IntStream.range;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.assets.generated.model.AddWagonModelRequest;
import be.gert.trainapp.api.assets.generated.model.AddWagonRequest;
import be.gert.trainapp.api.assets.generated.model.WagonTypeEnum;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonType;
import be.gert.trainapp.sm.assets.wagon.AddWagonUseCase;
import be.gert.trainapp.sm.assets.wagonmodel.AddWagonModelUseCase;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class WagonDataLoader {
	private final AddWagonModelUseCase addWagonModelUseCase;
	private final AddWagonUseCase addWagonUseCase;

	static WagonId wagonCoachId(int i) {
		return new WagonId("2024-364-0700-" + i);
	}
	static WagonId wagonBoxId(int i) {
		return new WagonId("2024-364-0701-" + i);
	}
	static WagonId wagonTankId(int i) {
		return new WagonId("2024-364-0702-" + i);
	}
	static WagonId wagonGondolaId(int i) {
		return new WagonId("2024-364-0703-" + i);
	}

	void loadWagons() {
		addWagons(WagonType.COACH, range(1, 5).mapToObj(WagonDataLoader::wagonCoachId).toList());
		addWagons(WagonType.BOX, range(1, 4).mapToObj(WagonDataLoader::wagonBoxId).toList());
		addWagons(WagonType.TANK, range(1, 3).mapToObj(WagonDataLoader::wagonTankId).toList());
		addWagons(WagonType.GONDOLA, range(1, 2).mapToObj(WagonDataLoader::wagonGondolaId).toList());
	}

	private void addWagons(WagonType type, List<WagonId> wagonIds) {
		addWagonModelUseCase.execute(new AddWagonModelRequest()
				.id(type.name())
				.name(type.name() + "-name")
				.gauge(standardGauge.type())
				.type(WagonTypeEnum.fromValue(type.name())));
		wagonIds.forEach(wagonId -> {
			addWagonUseCase.execute(new AddWagonRequest()
					.wagonId(wagonId.id())
					.modelTypeId(type.name())
					.serialNumber(wagonId.id()));
		});
	}

}
