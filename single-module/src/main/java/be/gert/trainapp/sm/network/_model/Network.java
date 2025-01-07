package be.gert.trainapp.sm.network._model;

import be.gert.trainapp.sm.network.NetworkId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//<editor-fold desc="EntityDef">
@Table(name = "NETWORK", schema = "NETWORK")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
//</editor-fold>
public class Network {
	private @EmbeddedId NetworkId id;
	private String name;

	public static Network newNetwork(NetworkId id, String name) {
		return new Network().id(id).name(name);
	}
}
