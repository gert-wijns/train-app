package be.gert.trainapp.sm.assets.asset.model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.assets.AssetId;
import be.gert.trainapp.sm.assets.SerialNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "ASSET", schema = "ASSETS")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@With
@NoArgsConstructor
@AllArgsConstructor
@ToString
//</editor-fold>
public class Asset extends JpaEntity<AssetId> {
	private @EmbeddedId AssetId id;
	private @Enumerated(EnumType.STRING) AssetType type;
	private @Column String name;
	private @Column String subtype;
	private @Embedded SerialNumber serialNumber;

	public static Asset newAsset(AssetId assetId, AssetType type, String name, String subtype, SerialNumber serialNumber) {
		return new Asset().id(assetId).type(type).name(name).subtype(subtype).serialNumber(serialNumber);
	}
}
