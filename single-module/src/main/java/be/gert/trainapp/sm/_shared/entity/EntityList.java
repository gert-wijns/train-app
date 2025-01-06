package be.gert.trainapp.sm._shared.entity;

import java.util.List;
import java.util.Optional;

public record EntityList<ID, T extends JpaEntity<ID>>(List<T> entities) {
	public static <ID, T extends JpaEntity<ID>> EntityList<ID, T> entityList(List<T> entities) {
		return new EntityList<>(entities);
	}

	public boolean contains(ID id) {
		return find(id).isPresent();
	}

	public T get(ID id) {
		return find(id).orElseThrow();
	}

	public Optional<T> find(ID id) {
		return entities.stream().filter(e -> e.id().equals(id)).findFirst();
	}
}
