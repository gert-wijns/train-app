package be.gert.trainapp.sm._shared.requirements;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "code", callSuper = false)
public class RequirementException extends RuntimeException {
	String code;
}
