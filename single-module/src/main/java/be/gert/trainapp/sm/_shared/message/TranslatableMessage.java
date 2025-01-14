package be.gert.trainapp.sm._shared.message;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TranslatableMessage {
	public enum TranslatableMessageParamType {
		STRING, NUMBER, LOCAL_DATE, TIME, LOCAL_DATE_TIME, ZONED_DATE_TIME, KEY
	}
	public record TranslatableMessageParam(String key, TranslatableMessageParamType type, Serializable value) {
		public String toFormattedString() {
			return switch (type) {
				case STRING, TIME, LOCAL_DATE, LOCAL_DATE_TIME, ZONED_DATE_TIME, KEY -> value.toString();
				case NUMBER -> ((BigDecimal) value).toPlainString();
			};
		}
	}
	public enum Severity {
		INFO, WARN, ERROR;
	}

	private final Severity severity;
	private final String key;
	private final String message;
	private final List<TranslatableMessage.TranslatableMessageParam> params = new ArrayList<>();
	private final List<TranslatableMessage> subMessages = new ArrayList<>();

	public static TranslatableMessage info(String key, String message) {
		return new TranslatableMessage(Severity.INFO, key, message);
	}

	public static TranslatableMessage warn(String key, String message) {
		return new TranslatableMessage(Severity.WARN, key, message);
	}

	public static TranslatableMessage error(String key, String message) {
		return new TranslatableMessage(Severity.ERROR, key, message);
	}

	public TranslatableMessage withParam(TranslatableMessageParam param) {
		params.add(param);
		return this;
	}

	public record KeyParam(String key) {
		public static KeyParam key(String key) {
			return new KeyParam(key);
		}
	}
	public TranslatableMessage withParam(String key, KeyParam value) {
		params.add(new TranslatableMessageParam(key, TranslatableMessageParamType.KEY, value.key));
		return this;
	}
	public TranslatableMessage withParam(String key, String value) {
		params.add(new TranslatableMessageParam(key, TranslatableMessageParamType.STRING, value));
		return this;
	}
	public TranslatableMessage withParam(String key, BigDecimal value) {
		params.add(new TranslatableMessageParam(key, TranslatableMessageParamType.NUMBER, value));
		return this;
	}
	public TranslatableMessage withParam(String key, LocalDate value) {
		params.add(new TranslatableMessageParam(key, TranslatableMessageParamType.LOCAL_DATE, value));
		return this;
	}
	public TranslatableMessage withParam(String key, LocalTime value) {
		params.add(new TranslatableMessageParam(key, TranslatableMessageParamType.TIME, value));
		return this;
	}
	public TranslatableMessage withParam(String key, LocalDateTime value) {
		params.add(new TranslatableMessageParam(key, TranslatableMessageParamType.LOCAL_DATE_TIME, value));
		return this;
	}
	public TranslatableMessage withParam(String key, ZonedDateTime value) {
		params.add(new TranslatableMessageParam(key, TranslatableMessageParamType.ZONED_DATE_TIME, value));
		return this;
	}

	public TranslatableMessage withSubMessage(TranslatableMessage message) {
		subMessages.add(message);
		return this;
	}

	public String toFormattedString() {
		String msg = "[" + this.severity + "|" + key + "] " + this.message;
		for (var param: params) {
			msg = msg.replace("${" + param.key + "}", param.toFormattedString());
		}
		if (subMessages.isEmpty()) {
			return msg;
		}
		return msg + "\n   " + subMessages.stream()
				.map(TranslatableMessage::toFormattedString)
					.collect(Collectors.joining("\n"))
				.replace("\n", "\n   ");
	}

	public DomainException asException(DomainExceptionType type) {
		return new DomainException(key, type, this);
	}
}
