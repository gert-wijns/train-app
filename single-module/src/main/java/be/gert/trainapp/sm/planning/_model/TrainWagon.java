package be.gert.trainapp.sm.planning._model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.WagonId;
import be.gert.trainapp.sm.assets.WagonModelId;
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
	private @Embedded WagonModelId modelId;
	private @ManyToOne Train train;

	public static TrainWagon newTrainWagon(WagonId id, WagonModelId modelId) {
		return new TrainWagon().id(id).modelId(modelId);
	}

	TrainWagon attachToTrain(Train train) {
		return train(train);
	}
}
