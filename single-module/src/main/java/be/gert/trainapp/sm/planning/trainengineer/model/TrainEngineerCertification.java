package be.gert.trainapp.sm.planning.trainengineer.model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm._shared.values.LocalDateRange;
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
@Table(name = "TRAIN_ENGINEER_CERTIFICATION", schema = "PLANNING")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//</editor-fold>
public class TrainEngineerCertification extends JpaEntity<TrainEngineerCertificationId> {
	private @EmbeddedId TrainEngineerCertificationId id;
	private @Embedded LocalDateRange validRange;
}
