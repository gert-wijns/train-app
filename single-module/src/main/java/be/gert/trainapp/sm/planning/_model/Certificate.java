package be.gert.trainapp.sm.planning._model;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
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

//<editor-fold desc="EntityDef">
@Table(name = "CERTIFICATE", schema = "PLANNING")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "", toBuilder = true)
//</editor-fold>
public class Certificate extends JpaEntity<CertificateCode> {
	private @EmbeddedId CertificateCode id;
	private String description;
}
