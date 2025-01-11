package be.gert.trainapp.sm.usermanagement._model;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import be.gert.trainapp.sm.usermanagement.UserId;
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
@Table(name = "USERS", schema = "USER_MANAGEMENT")
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(builderMethodName = "", toBuilder = true)
//</editor-fold>
public class User extends JpaEntity<UserId> {
    private @EmbeddedId UserId id;
    private String password;
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<String> roles;

    public User grantRoles(@NotNull List<String> roles) {
        this.roles.addAll(roles);
        return this;
    }
}
