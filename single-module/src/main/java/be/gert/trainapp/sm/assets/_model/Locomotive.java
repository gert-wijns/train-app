package be.gert.trainapp.sm.assets._model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.network.TrackGauge;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//<editor-fold desc="EntityDef">
@Table(name = "LOCOMOTIVE", schema = "ASSETS")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//</editor-fold>
public class Locomotive extends JpaEntity<LocomotiveId> {
	private @EmbeddedId LocomotiveId id;
	private @Embedded LocomotiveModelId modelId;
	private @Column(length = 30) String name;
	private @Embedded SerialNumber serialNumber;
	private @Embedded TrackGauge gauge;

	public static Locomotive newLocomotive(LocomotiveId id,
	                                       String name,
	                                       LocomotiveModelId modelId,
	                                       SerialNumber serialNumber,
	                                       TrackGauge gauge) {
		return new Locomotive()
				.id(id)
				.modelId(modelId)
				.name(name)
				.serialNumber(serialNumber)
				.gauge(gauge);
	}
}
