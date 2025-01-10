package be.gert.trainapp.sm.personnel._model;

import static be.gert.trainapp.sm.personnel.EmployeeRole.UNASSIGNED;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm._shared.values.FullName;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel.EmployeeRole;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//<editor-fold desc="EntityDef">
@Table(name = "EMPLOYEE", schema = "PERSONNEL")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
//</editor-fold>
public class Employee extends JpaEntity<EmployeeId> {
	private @EmbeddedId EmployeeId id;
	private @Embedded @Setter FullName fullName;
	private @Enumerated(EnumType.STRING) EmployeeRole role;

	public static Employee newEmployee(EmployeeId employeeId) {
		return new Employee().id(employeeId).role(UNASSIGNED);
	}

	public Employee assignRole(EmployeeRole role) {
		this.role = role;
		return this;
	}
}
