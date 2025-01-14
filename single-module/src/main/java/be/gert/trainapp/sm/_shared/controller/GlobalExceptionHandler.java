package be.gert.trainapp.sm._shared.controller;

import static org.springframework.http.ResponseEntity.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.gert.trainapp.api.assets.generated.model.TranslatableMessageParamResponse;
import be.gert.trainapp.api.assets.generated.model.TranslatableMessageParamResponse.TypeEnum;
import be.gert.trainapp.api.assets.generated.model.TranslatableMessageResponse;
import be.gert.trainapp.api.assets.generated.model.TranslatableMessageResponse.SeverityEnum;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType;
import be.gert.trainapp.sm._shared.message.TranslatableMessage;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.TranslatableMessageParam;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
	private final ObjectMapper objectMapper;

	@SneakyThrows
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<TranslatableMessageResponse> domainException(DomainException ex) {
		return status(map(ex.type()))
				.contentType(MediaType.APPLICATION_JSON)
				.body(map(ex.message()));
	}

	private HttpStatus map(DomainExceptionType type) {
		return switch (type) {
			case CONFLICT -> HttpStatus.CONFLICT;
			case NOT_FOUND -> HttpStatus.NOT_FOUND;
		};
	}

	private TranslatableMessageResponse map(TranslatableMessage message) {
		return new TranslatableMessageResponse()
				.message(message.message())
				.key(message.key())
				.severity(SeverityEnum.fromValue(message.severity().name()))
				.params(message.params().stream().map(this::map).toList())
				.subMessages(message.subMessages().stream().map(this::map).toList());
	}

	@SneakyThrows
	private TranslatableMessageParamResponse map(TranslatableMessageParam param) {
		return new TranslatableMessageParamResponse()
				.key(param.key())
				.value(param.value() instanceof String s ?
						s: objectMapper.writeValueAsString(param.value()))
				.type(TypeEnum.fromValue(param.type().name()));
	}
}
