package be.gert.trainapp.sm._shared.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Provides utility methods for Lists of entities.
 * <br />
 * Never mutates the original list.
 * <br />
 * <ul>
 *     <li>entityList(entities).map(setter).whereId(id)</li>
 *     <li>entityList(entities).map(setter).where(predicate)</li>
 *     <li>entityList(entities).delete(id)</li>
 *     <li>entityList(entities).get(id)</li>
 *     <li>entityList(entities).append(entities)</li>
 * </ul>
 */
public final class EntityList {
	public record EntityListSet<ID, E extends JpaEntity<ID>>(List<E> entities) {
		public EntityListWhere<ID, E> map(UnaryOperator<E> setter) {
			return test -> entities.stream()
					.map(d -> test.test(d) ? setter.apply(d) : d)
					.toList();
		}

		public List<E> delete(ID id) {
			return entities.stream().filter(e -> !e.id().equals(id)).toList();
		}

		@SafeVarargs
		public final List<E> append(E... entitiesToAppend) {
			if (entitiesToAppend.length == 0) {
				return entities;
			}
			// entity list is not expected to contain duplicate ids
			Set<ID> ids = new HashSet<>();
			for (E entityToAppend : entitiesToAppend) {
				if (!ids.add(entityToAppend.id())) {
					throw new IllegalArgumentException("entitiesToAppend contains duplicate id: " + entityToAppend.id());
				}
				if (entities.stream().anyMatch(e -> e.id().equals(entityToAppend.id()))) {
					throw new IllegalArgumentException("Entity with id " + entityToAppend.id() +
							" already exists in list. Add is not applicable!");
				}
			}
			return Stream.concat(entities.stream(), Stream.of(entitiesToAppend)).toList();
		}

		public Optional<E> find(ID id) {
			return entities.stream().filter(e -> e.id().equals(id)).findFirst();
		}

		public E get(ID id) {
			return find(id).orElseThrow();
		}

		public List<E> replace(E toUpdate) {
			return map(e -> toUpdate).whereId(toUpdate.id());
		}
	}

	/**
	 * Can select an item by id or more generally by predicate.
	 */
	public interface EntityListWhere<ID, E extends JpaEntity<ID>> {
		default List<E> whereId(ID id) {
			return where(e -> e.id().equals(id));
		}

		List<E> where(Predicate<E> test);
	}

	public static <ID, E extends JpaEntity<ID>> EntityListSet<ID, E> entityList(List<E> entities) {
		return new EntityListSet<>(entities);
	}

}
