package be.gert.trainapp.sm._shared.message;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.info;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.warn;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import be.gert.trainapp.sm._shared.clock.AppClock;

class TranslatableMessageTest {

	@Test
	void testInfo() {
		assertThat(info("MSG_KEY", "All is well!")
				.toFormattedString())
				.isEqualTo("[INFO|MSG_KEY] All is well!");
	}

	@Test
	void testInfoWithParam() {
		assertThat(info("MSG_KEY", "Hello ${name}!")
				.withParam("name", "Bob")
				.toFormattedString())
				.isEqualTo("[INFO|MSG_KEY] Hello Bob!");
	}

	@Test
	void testInfoWithParams() {
		assertThat(info("MSG_KEY", "Hello ${name}, the weather is ${weather} today, right?!")
				.withParam("name", "Bob")
				.withParam("weather", "great")
				.toFormattedString())
				.isEqualTo("[INFO|MSG_KEY] Hello Bob, the weather is great today, right?!");
	}

	@Test
	void testInfoWithLocalDateParam() {
		assertThat(info("MSG_KEY", "Param: ${param}")
				.withParam("param", LocalDate.now(AppClock.clock))
				.toFormattedString())
				.isEqualTo("[INFO|MSG_KEY] Param: 2024-12-20");
	}

	@Test
	void testInfoWithLocalTimeParam() {
		assertThat(info("MSG_KEY", "Param: ${param}")
				.withParam("param", LocalTime.now(AppClock.clock))
				.toFormattedString())
				.isEqualTo("[INFO|MSG_KEY] Param: 12:00");
	}

	@Test
	void testInfoWithLocalDateTimeParam() {
		assertThat(info("MSG_KEY", "Param: ${param}")
				.withParam("param", LocalDateTime.now(AppClock.clock))
				.toFormattedString())
				.isEqualTo("[INFO|MSG_KEY] Param: 2024-12-20T12:00");
	}

	@Test
	void testInfoWithZonedDateTimeParam() {
		assertThat(info("MSG_KEY", "Param: ${param}")
				.withParam("param", ZonedDateTime.now(AppClock.clock))
				.toFormattedString())
				.isEqualTo("[INFO|MSG_KEY] Param: 2024-12-20T12:00Z[UTC]");
	}

	@Test
	void testInfoWithSubMessages() {
		assertThat(info("MSG_KEY", "Hello ${name}!")
				.withParam("name", "Bob")
				.withSubMessage(warn("SUB_KEY1", "It's getting chilly!")
						.withSubMessage(warn("SUB_SUB", "Let's go inside")))
				.withSubMessage(info("SUB_KEY2", "It's time to eat :)"))
				.toFormattedString())
				.isEqualTo("""
					[INFO|MSG_KEY] Hello Bob!
					   [WARN|SUB_KEY1] It's getting chilly!
					      [WARN|SUB_SUB] Let's go inside
					   [INFO|SUB_KEY2] It's time to eat :)""");
	}

}
