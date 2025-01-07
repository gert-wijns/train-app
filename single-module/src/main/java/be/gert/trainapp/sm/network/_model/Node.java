package be.gert.trainapp.sm.network._model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm._shared.values.GeoPosition;
import be.gert.trainapp.sm.network.NetworkId;
import be.gert.trainapp.sm.network.NodeId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//<editor-fold desc="EntityDef">
@Table(name = "NODE", schema = "NETWORK")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
//</editor-fold>
public class Node extends JpaEntity<NodeId> {
	private @EmbeddedId NodeId id;
	private @Embedded GeoPosition geoPosition;
	private @Embedded NetworkId networkId;
	private String name;
	private boolean decommissioned;

	public static Node newNode(NetworkId networkId, NodeId id, String name, GeoPosition geoPosition) {
		return new Node().networkId(networkId).id(id).name(name).geoPosition(geoPosition);
	}

	public Node rename(@NotNull String name) {
		return name(name);
	}

	public Node reposition(GeoPosition geoPosition) {
		return geoPosition(geoPosition);
	}

	public Node decommission() {
		return decommissioned(true);
	}
}
