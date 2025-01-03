package be.gert.trainapp.sm.planning.train.model;

import static be.gert.trainapp.sm.planning.train.model.TrainExceptions.locomotiveAlreadySet;
import static be.gert.trainapp.sm.planning.train.model.TrainExceptions.wagonAlreadyAdded;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.planning.TrainId;
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
@Table(name = "TRAIN", schema = "PLANNING")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@With
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
//</editor-fold>
public class Train extends JpaEntity<TrainId> {
	private @EmbeddedId TrainId id;
	private @Embedded LocomotiveId locomotiveId;
	@JdbcTypeCode(SqlTypes.JSON)
	private List<WagonId> wagonIds;
	private boolean readyForUse;

	public static Train newTrain(TrainId id) {
		return new Train().id(id);
	}

	public Train addLocomotive(LocomotiveId locomotiveId) {
		if (this.locomotiveId != null) {
			throw locomotiveAlreadySet(id, this.locomotiveId);
		}
		this.locomotiveId = locomotiveId;
		return this;
	}

	public Train addWagon(WagonId wagonId) {
		if (wagonIds.contains(wagonId)) {
			throw wagonAlreadyAdded(id, wagonId);
		}
		wagonIds.add(wagonId);
		return this;
	}
}
