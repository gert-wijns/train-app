package be.gert.trainapp.sm.planning._model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

import java.util.ArrayList;
import java.util.List;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.personnel.EmployeeId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Table(name = "TRAIN_ENGINEER", schema = "PLANNING")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
//</editor-fold>
public class TrainEngineer extends JpaEntity<EmployeeId> {
	private @EmbeddedId EmployeeId id;
	private boolean active;
	@OneToMany(fetch = EAGER, cascade = ALL, mappedBy = "id.employeeId")
	private List<TrainEngineerCertification> certifications;

	public static TrainEngineer newTrainEngineer(EmployeeId id) {
		return new TrainEngineer()
				.id(id)
				.active(false)
				.certifications(new ArrayList<>(0));
	}

	public TrainEngineer activate() {
		return active(true);
	}

	public TrainEngineer inactivate() {
		return active(false);
	}
}
