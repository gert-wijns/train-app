package be.gert.trainapp.sm._localhost;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.network.generated.model.AddNodeRequest;
import be.gert.trainapp.api.network.generated.model.AddTrackRequest;
import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.api.network.generated.model.SpeedBody;
import be.gert.trainapp.api.network.generated.model.SpeedBody.MeasurementEnum;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.network.node.AddNodeUseCase;
import be.gert.trainapp.sm.network.track.AddTrackUseCase;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class NetworkDataLoader {
	static final TrackGauge standardGauge = new TrackGauge("1435mm");

	private final AddNodeUseCase addNodeUseCase;
	private final AddTrackUseCase addTrackUseCase;

	void loadNetworks() {
		networkBrusselsToAntwerp();
	}

	private void networkBrusselsToAntwerp() {
		addTracks(
				addNode("Brussels-Central", 235, 10806),
				addNode("Brussels-Central_Brussels-North_1", 254, 10734),
				addNode("Brussels-Central_Brussels-North_2", 297, 10689),
				addNode("Brussels-Central_Brussels-North_3", 348, 10574),
				addNode("Brussels-Central_Brussels-North_4", 299, 10488),
				addNode("Brussels-North", 317, 10401),
				addNode("Brussels-North_Schaarbeek_1", 391, 10118),
				addNode("Schaarbeek", 602, 9892),
				addNode("Schaarbeek_Buda-1", 766, 9809),
				addNode("Schaarbeek_Buda-2", 994, 9763),
				addNode("Schaarbeek_Buda-3", 1165, 9582),
				addNode("Schaarbeek_Buda-4", 1176, 9274),
				addNode("Buda", 1297, 9070),
				addNode("Vilvoorde", 1569, 8616),
				addNode("Eppegem", 2000,7668),
				addNode("Weerde", 2240,7140),
				addNode("Weerde_Mechelen_1", 2309,6724),
				addNode("Weerde_Mechelen_2", 2299,6269),
				addNode("Mechelen", 2452, 6024),
				addNode("Mechelen_Mechelen-Nekkerspoel_1", 2546, 5898),
				addNode("Mechelen-Nekkerspoel", 2571, 5669),
				addNode("Sint-Katelijne-Waver", 2662, 4555),
				addNode("Sint-Katelijne-Waver_Duffel_1", 2664, 4249),
				addNode("Duffel", 2610, 3977),
				addNode("Duffel_Kontich_1", 2497, 3287),
				addNode("Kontich", 2335, 2803),
				addNode("Hove", 2139, 2262),
				addNode("Mortsel-Deurnsesteenweg", 1815, 1450),
				addNode("Antwerp-Berchem", 1550, 982),
				addNode("Antwerp-Berchem_Antwerp-Central_1", 1379, 658),
				addNode("Antwerpen-Central", 1361, 507));
	}

	private void addTracks(NodeId... nodeIds) {
		for (int i = 1; i < nodeIds.length; i++) {
			addTrackUseCase.execute(new AddTrackRequest()
					.gauge("1435mm")
					.fromNodeId(nodeIds[i-1].id())
					.toNodeId(nodeIds[i].id())
					.slope(BigDecimal.ZERO)
					.speedLimit(new SpeedBody()
							.speed(new BigDecimal(70))
							.measurement(MeasurementEnum.KPH))
					.gauge(standardGauge.type())
					.electrified(true));
		}
	}

	//dummy data:
	//maxLongitude: 2664 >
	//minLongitude: 235   > offset -1214
	//maxLatitude: 10806 >
	//minLatitude: 507    > offset -5149
	private NodeId addNode(String name, int x, int y) {
		addNodeUseCase.execute(new AddNodeRequest()
				.id(name)
				.name(name.contains("_") ? "": name.replace("_", " "))
				.geoPosition(new GeoPositionBody()
						.longitude(new BigDecimal(x - 1214).setScale(7, HALF_UP)
								.divide(new BigDecimal(1000).setScale(7, HALF_UP), HALF_UP))
						.latitude(new BigDecimal(y-5149).setScale(7, HALF_UP)
								.divide(new BigDecimal(1000).setScale(7, HALF_UP), HALF_UP))));
		return new NodeId(name);
	}
}
