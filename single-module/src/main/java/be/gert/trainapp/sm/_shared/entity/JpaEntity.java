package be.gert.trainapp.sm._shared.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.ToString;

/**
 * Marker for aggregate roots, this is the entry point for business methods.
 */
@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
@ToString(callSuper = true)
public abstract class JpaEntity<ID> {
	@Version
	private Integer version;
	@LastModifiedBy
	private String lastModifiedBy;
	@LastModifiedDate
	private Instant lastModifiedDate;
	@CreatedBy
	private String createdBy;
	@CreatedDate
	private Instant createdDate;

	public abstract ID id();
}
