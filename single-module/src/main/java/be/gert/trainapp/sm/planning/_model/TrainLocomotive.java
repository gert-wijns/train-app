package be.gert.trainapp.sm.planning._model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "TRAIN_LOCOMOTIVE", schema = "PLANNING")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
//</editor-fold>
public class TrainLocomotive extends JpaEntity<LocomotiveId> {
	private @EmbeddedId LocomotiveId id;
	private @Embedded LocomotiveModelId modelId;
	private @OneToOne(optional=false, mappedBy="locomotive") Train train;

	public static TrainLocomotive newTrainLocomotive(LocomotiveId id, LocomotiveModelId modelId) {
		return new TrainLocomotive().id(id).modelId(modelId);
	}

	TrainLocomotive attachToTrain(Train train) {
		return train(train);
	}
}
