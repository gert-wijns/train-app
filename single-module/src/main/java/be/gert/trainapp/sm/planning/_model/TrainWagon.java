package be.gert.trainapp.sm.planning._model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.SerialNumber;
import be.gert.trainapp.sm.assets.WagonId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//<editor-fold desc="EntityDef">
@Table(name = "TRAIN_WAGON", schema = "PLANNING")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
//</editor-fold>
public class TrainWagon extends JpaEntity<WagonId> {
	private @EmbeddedId WagonId id;
	private @Embedded SerialNumber serialNumber;
	private boolean decommissioned;
	private @ManyToOne Train train;
	private String cargo;

	public TrainWagon decommission() {
		return decommissioned(true);
	}

	public TrainWagon loadCargo(String cargo) {
		return cargo(cargo);
	}

	public TrainWagon unloadCargo() {
		return cargo(null);
	}
}
