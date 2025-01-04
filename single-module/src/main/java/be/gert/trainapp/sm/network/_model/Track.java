package be.gert.trainapp.sm.network._model;

import java.math.BigDecimal;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm._shared.values.Speed;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.network.TrackId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//<editor-fold desc="EntityDef">
@Table(name = "NODE_RELATION", schema = "NETWORK")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
//</editor-fold>
public class Track extends JpaEntity<TrackId> {
	private @EmbeddedId TrackId id;
	private boolean electrified;
	private BigDecimal slope;
	private @Embedded Speed speedLimit;
	private @Embedded TrackGauge gauge;
	private boolean decomissioned;

	public static Track newTrack(TrackId id, TrackGauge gauge, BigDecimal slope) {
		return new Track().id(id).gauge(gauge).slope(slope);
	}

	public Track electrify() {
		electrified = true;
		return this;
	}

	public Track adjustSpeedLimit(Speed speedLimit) {
		this.speedLimit = speedLimit;
		return this;
	}

	public Track decomission() {
		return decomissioned(true);
	}
}
