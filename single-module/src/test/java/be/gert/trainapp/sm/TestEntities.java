package be.gert.trainapp.sm;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.stereotype.Component;

import be.gert.trainapp.sm._shared.entity.JpaEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestEntities {
	private final EntityManager entityManager;

	public <ID, E extends JpaEntity<ID>> void assertState(E entity) {
		assertThat(entityManager.find(entity.getClass(), entity.id()))
				.isNotNull()
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes(
						".*.createdDate",
						".*.lastModifiedDate",
						".*.lastModifiedBy",
						".*.createdBy",
						".*.version")
				.ignoringFields(
						"createdDate",
						"lastModifiedDate",
						"lastModifiedBy",
						"createdBy",
						"version")
				.isEqualTo(entity);
	}

	public <ID, E extends JpaEntity<ID>> E save(E entity) {
		entityManager.persist(entity);
		return entity;
	}
}
