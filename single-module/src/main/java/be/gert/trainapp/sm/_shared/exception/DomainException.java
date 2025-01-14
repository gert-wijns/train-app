package be.gert.trainapp.sm._shared.exception;

import be.gert.trainapp.sm._shared.message.TranslatableMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"code"}, callSuper = false)
public class DomainException extends RuntimeException {
	public enum DomainExceptionType {
		NOT_FOUND,
		CONFLICT
	}
	private final String code;
	private final DomainExceptionType type;
	private final TranslatableMessage message;

	public DomainException(String code, DomainExceptionType type, TranslatableMessage message) {
		super(message.toFormattedString());
		this.code = code;
		this.type = type;
		this.message = message;
	}

	@Override
	public String toString() {
		return "DomainException{" +
				"code='" + code + '\'' +
				", type=" + type +
				", message='" + getFormattedMessage() +
				'}';
	}

	public String getFormattedMessage() {
		return message.toFormattedString();
	}

}
