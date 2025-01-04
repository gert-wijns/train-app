package be.gert.trainapp.sm.network.given;

import static be.gert.trainapp.sm.network.given.NodeDefaults.stationAntwerpId;
import static be.gert.trainapp.sm.network.given.NodeDefaults.stationBrusselsId;
import static be.gert.trainapp.sm.network.given.SpeedDefaults.kilometersPerHour;

import java.math.BigDecimal;
import java.math.RoundingMode;

import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.network.TrackId;
import be.gert.trainapp.sm.network.track.model.Track;

public class TrackDefaults {

	public static final TrackId trackAntwerpBrusselsId = new TrackId(stationAntwerpId, stationBrusselsId);
	public static final TrackGauge standardGauge = new TrackGauge("1435mm");

	public static Track trackAntwerpBrussels() {
		return new Track(trackAntwerpBrusselsId,
				true,
				new BigDecimal(3).setScale(2, RoundingMode.UP),
				kilometersPerHour(50),
				standardGauge,
				false);
	}
}
