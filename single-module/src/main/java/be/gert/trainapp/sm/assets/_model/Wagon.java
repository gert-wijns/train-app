package be.gert.trainapp.sm.assets._model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.network.TrackGauge;
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
@Table(name = "WAGON", schema = "ASSETS")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
//</editor-fold>
public class Wagon extends JpaEntity<WagonId> {
	private @EmbeddedId WagonId id;
	private @Embedded WagonModelId modelId;
	private @Embedded SerialNumber serialNumber;
	private @Embedded TrackGauge gauge;

	public static Wagon newWagon(WagonId wagonId,
	                             WagonModelId wagonModelId,
	                             SerialNumber serialNumber,
	                             TrackGauge gauge) {
		return new Wagon()
				.id(wagonId)
				.modelId(wagonModelId)
				.serialNumber(serialNumber)
				.gauge(gauge);
	}

}
