package be.gert.trainapp.sm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.assertj.core.api.AbstractCollectionAssert;
import org.assertj.core.api.ObjectAssert;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ValidationAssertionDefaults {
	private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

	public static <T> AbstractCollectionAssert<?, Collection<? extends T>, T, ObjectAssert<T>> assertValid(Collection<T> collection) {
		assertThat(collection)
			.isNotNull()
			.flatMap(VALIDATOR::validate)
			.isEmpty();
		return assertThat(collection);
	}

	public static <T> ObjectAssert<T> assertValid(T result) {
		assertThat(result).isNotNull();
		assertThat(VALIDATOR.validate(result)).isEmpty();
		return assertThat(result);
	}
}
