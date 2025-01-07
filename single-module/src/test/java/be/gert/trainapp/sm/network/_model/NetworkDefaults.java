package be.gert.trainapp.sm.network._model;

import be.gert.trainapp.sm.network.NetworkId;

public class NetworkDefaults {
	public static NetworkId networkBelgiumId = new NetworkId("network-be");

	public static Network networkBelgium() {
		return Network.builder()
				.id(networkBelgiumId)
				.name("Belgium")
				.build();
	}
}
