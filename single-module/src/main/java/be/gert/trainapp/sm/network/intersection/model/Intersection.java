package be.gert.trainapp.sm.network.intersection.model;

import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm._shared.entity.JpaEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

//<editor-fold desc="EntityDef">
@Table(name = "INTERSECTION", schema = "NETWORK")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@With
@NoArgsConstructor
@AllArgsConstructor
//</editor-fold>
public class Intersection extends JpaEntity<NodeId> {
	private @EmbeddedId NodeId id;
}
