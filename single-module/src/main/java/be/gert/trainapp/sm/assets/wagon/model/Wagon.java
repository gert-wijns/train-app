package be.gert.trainapp.sm.assets.wagon.model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
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
import lombok.With;

//<editor-fold desc="EntityDef">
@Table(name = "WAGON", schema = "ASSETS")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@With
@NoArgsConstructor
@AllArgsConstructor
@ToString
//</editor-fold>
public class Wagon extends JpaEntity<WagonId> {
	private @EmbeddedId WagonId id;
	private @Embedded WagonModelId modelId;
	private @Embedded SerialNumber serialNumber;

	public static Wagon newWagon(WagonId wagonId, WagonModelId wagonModelId, SerialNumber serialNumber) {
		return new Wagon().id(wagonId).modelId(wagonModelId).serialNumber(serialNumber);
	}

}
