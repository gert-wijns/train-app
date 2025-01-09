package be.gert.trainapp.sm.usermanagement;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(@Column(length = 36) String id) {
}
