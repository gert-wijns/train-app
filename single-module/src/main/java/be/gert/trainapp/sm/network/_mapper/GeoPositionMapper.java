package be.gert.trainapp.sm.network._mapper;

import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.sm.network.GeoPosition;

public class GeoPositionMapper {
	public static GeoPositionBody toGeoPositionBody(GeoPosition geoPosition) {
		return geoPosition == null ? null: new GeoPositionBody()
				.longitude(geoPosition.longitude())
				.latitude(geoPosition.latitude());
	}

	public static GeoPosition toGeoPosition(GeoPositionBody geoPosition) {
		return geoPosition == null ? null: new GeoPosition(
				geoPosition.getLongitude(),
				geoPosition.getLatitude());
	}
}
