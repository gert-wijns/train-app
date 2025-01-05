package be.gert.trainapp.sm._shared.entity;

import java.util.List;

public record EntityList<ID, T extends JpaEntity<ID>>(List<T> entities) {
	public static <ID, T extends JpaEntity<ID>> EntityList<ID, T> entityList(List<T> entities) {
		return new EntityList<>(entities);
	}

	public boolean contains(ID id) {
		return entities.stream().anyMatch(e -> e.id().equals(id));
	}
}
