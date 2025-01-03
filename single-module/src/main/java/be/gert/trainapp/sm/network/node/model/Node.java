package be.gert.trainapp.sm.network.node.model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.network.GeoPosition;
import be.gert.trainapp.sm.network.NodeId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

//<editor-fold desc="EntityDef">
@Table(name = "NODE", schema = "NETWORK")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@With
@NoArgsConstructor
@AllArgsConstructor
@ToString
//</editor-fold>
public class Node extends JpaEntity<NodeId> {
	private @EmbeddedId NodeId id;
	private @Embedded GeoPosition geoPosition;
	private String name;

	public static Node newNode(NodeId id, String name, GeoPosition geoPosition) {
		return new Node().id(id).name(name).geoPosition(geoPosition);
	}

	public Node rename(@NotNull String name) {
		return name(name);
	}

	public Node reposition(GeoPosition geoPosition) {
		return geoPosition(geoPosition);
	}
}
