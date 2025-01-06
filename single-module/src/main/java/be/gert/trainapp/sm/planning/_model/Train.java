package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm._shared.entity.EntityList.entityList;
import static be.gert.trainapp.sm.assets.LocomotivePowerType.ELECTRIC;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static org.apache.commons.lang3.ObjectUtils.notEqual;

import java.util.List;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm._shared.values.GeoPosition;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.planning.RoutePlanId;
import be.gert.trainapp.sm.planning.TrainId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//<editor-fold desc="EntityDef">
@Table(name = "TRAIN", schema = "PLANNING")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
//</editor-fold>
public class Train extends JpaEntity<TrainId> {
	private @EmbeddedId TrainId id;
	private @Embedded TrainLocomotive locomotive;
	private @OneToMany(mappedBy = "train", cascade = ALL, fetch = EAGER) List<TrainWagon> wagons;
	private @Embedded TrackGauge gauge;
	private @Embedded GeoPosition position;
	private @Embedded RoutePlanId routePlanId;
	private boolean readyForUse;

	public static Train newTrain(TrainId id, Locomotive locomotive) {
		var trainLocomotive = new TrainLocomotive(
				locomotive.id(),
				locomotive.powerType() == ELECTRIC,
				locomotive.decommissioned());
		return new Train().id(id).gauge(locomotive.gauge()).locomotive(trainLocomotive);
	}

	public Train attachWagon(Wagon wagon) {
		if (entityList(wagons).contains(wagon.id())) {
			throw PlanningModelExceptions.wagonAlreadyAdded(id, wagon.id());
		}
		wagons.add(new TrainWagon(wagon.id(), wagon.decommissioned(), this));
		return this;
	}

	public Train locomotiveDecommissioned() {
		this.locomotive = locomotive.withDecommissioned(true);
		return this;
	}

	public Train wagonDecommissioned(WagonId id) {
		entityList(wagons).get(id).decommission();
		return this;
	}
}
