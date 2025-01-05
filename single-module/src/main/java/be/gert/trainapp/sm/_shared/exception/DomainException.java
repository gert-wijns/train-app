package be.gert.trainapp.sm._shared.exception;

import be.gert.trainapp.sm._shared.message.TranslatableMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"code"}, callSuper = false)
public class DomainException extends RuntimeException {
	private final String code;
	private final TranslatableMessage message;

	public DomainException(String code, TranslatableMessage message) {
		super(message.toFormattedString());
		this.code = code;
		this.message = message;
	}

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
