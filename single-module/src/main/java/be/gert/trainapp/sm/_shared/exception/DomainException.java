package be.gert.trainapp.sm._shared.exception;

import be.gert.trainapp.sm._shared.message.TranslatableMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode(of = {"code"}, callSuper = false)
@RequiredArgsConstructor
public class DomainException extends RuntimeException {
	private final String code;
	private final TranslatableMessage message;

	@Override
	public String toString() {
		return "DomainException{" +
				"code='" + code + '\'' +
				", message='" + getFormattedMessage() +
				'}';
	}

	public String getFormattedMessage() {
		return message.toFormattedString();
	}

}
