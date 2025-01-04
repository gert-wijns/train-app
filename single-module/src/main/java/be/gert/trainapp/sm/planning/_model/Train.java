package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

import java.util.List;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.values.GeoPosition;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.planning.TrainId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	private @OneToOne TrainLocomotive locomotive;
	private @OneToMany(mappedBy = "train", cascade = ALL, fetch = EAGER) List<TrainWagon> wagons;
	private @Embedded TrackGauge gauge;
	private @Embedded GeoPosition position;
	//private @Embedded RoutePlan planId;
	private boolean readyForUse;

	public static Train newTrain(TrainId id) {
		return new Train().id(id);
	}

	public static DomainException locomotiveAlreadySet(TrainId trainId, LocomotiveId locomotiveId) {
		return error("PLANNING_TRAIN_LOCOMOTIVE_ALREADY_SET",
				"Train '${id}' already has a locomotive with id '${locomotiveId}'.")
				.withParam("id", trainId.id())
				.withParam("locomotiveId", locomotiveId.id())
				.asException();
	}

	public Train addLocomotive(TrainLocomotive locomotive) {
		if (this.locomotive != null) {
			throw locomotiveAlreadySet(id, this.locomotive.id());
		}
		this.locomotive = locomotive;
		locomotive.attachToTrain(this);
		return this;
	}

	public static DomainException wagonAlreadyAdded(TrainId trainId, WagonId wagonId) {
		return error("PLANNING_TRAIN_WAGON_ALREADY_ADDED",
				"Train '${id}' already has wagon with id '${wagonId}'.")
				.withParam("id", trainId.id())
				.withParam("wagonId", wagonId.id())
				.asException();
	}

	public Train addWagon(TrainWagon wagon) {
		if (wagons.contains(wagon)) {
			throw wagonAlreadyAdded(id, wagon.id());
		}
		wagons.add(wagon);
		wagon.attachToTrain(this);
		return this;
	}
}
