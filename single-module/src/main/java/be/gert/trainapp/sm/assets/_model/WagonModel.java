package be.gert.trainapp.sm.assets._model;

import static jakarta.persistence.EnumType.STRING;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets.WagonType;
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
@Table(name = "WAGON_MODEL", schema = "ASSETS")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(builderMethodName = "", toBuilder = true)
//</editor-fold>
public class WagonModel extends JpaEntity<WagonModelId> {
	private @EmbeddedId WagonModelId id;
	private @Column(length = 30) String name;
	private @Embedded TrackGauge gauge;
	private @Enumerated(STRING) WagonType type;

	public static WagonModel newWagonModel(WagonModelId id,
	                                       String name,
	                                       TrackGauge gauge,
	                                       WagonType type) {
		return new WagonModel().id(id).name(name).gauge(gauge).type(type);
	}
}
