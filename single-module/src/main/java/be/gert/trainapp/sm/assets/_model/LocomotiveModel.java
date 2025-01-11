package be.gert.trainapp.sm.assets._model;

import static jakarta.persistence.EnumType.STRING;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.LocomotivePowerType;
import be.gert.trainapp.sm.network.TrackGauge;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
@Table(name = "LOCOMOTIVE_MODEL", schema = "ASSETS")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(builderMethodName = "", toBuilder = true)
//</editor-fold>
public class LocomotiveModel extends JpaEntity<LocomotiveModelId> {
	private @EmbeddedId LocomotiveModelId id;
	private @Column(length = 30) String name;
	private @Enumerated(STRING) LocomotivePowerType powerType;
	private @Embedded TrackGauge gauge;

	public static LocomotiveModel newLocomotiveModel(LocomotiveModelId id,
	                                                 String name,
	                                                 LocomotivePowerType powerType,
	                                                 TrackGauge gauge) {
		return new LocomotiveModel().id(id).name(name).powerType(powerType).gauge(gauge);
	}
}
