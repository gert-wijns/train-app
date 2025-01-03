package be.gert.trainapp.sm.network;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import jakarta.persistence.Embeddable;

//Standard: 1435mm, Ireland: 1600mm, Spain: 1674mm, Portugal: 1665mm, Russia: 1524, Argentina, Chile : 1676 ...
@Embeddable
public record TrackGauge(String type) {
	public TrackGauge {
		assertNotNull(type);
	}
}
