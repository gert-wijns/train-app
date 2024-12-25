package be.gert.trainapp.sm.assets.locomotive.model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import jakarta.persistence.Column;
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
@Table(name = "LOCOMOTIVE", schema = "ASSETS")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@With
@NoArgsConstructor
@AllArgsConstructor
@ToString
//</editor-fold>
public class Locomotive extends JpaEntity<LocomotiveId> {
	private @EmbeddedId LocomotiveId id;
	private @Embedded LocomotiveModelId modelId;
	private @Column String name;

	public static Locomotive newLocomotive(LocomotiveId id, String name, LocomotiveModelId modelId) {
		return new Locomotive(id, modelId, name);
	}
}
