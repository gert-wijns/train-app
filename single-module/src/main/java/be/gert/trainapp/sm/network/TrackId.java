package be.gert.trainapp.sm.network;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import jakarta.persistence.Embeddable;

@Embeddable
public record TrackId(NodeId from, NodeId to) {
	public TrackId {
		assertNotNull(from);
		assertNotNull(to);
	}
}
