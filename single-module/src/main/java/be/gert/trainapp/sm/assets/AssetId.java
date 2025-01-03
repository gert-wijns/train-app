package be.gert.trainapp.sm.assets;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record AssetId(@Column(length = 36) String id) {
	public AssetId {
		requireNonNull(id);
	}
}
