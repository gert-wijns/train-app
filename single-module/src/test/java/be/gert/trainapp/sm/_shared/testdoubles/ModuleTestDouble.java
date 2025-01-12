package be.gert.trainapp.sm._shared.testdoubles;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate test doubles so we can gather them all and reset before test
 * It will also validated the test double is in fact a spy
 * (adr-011-test-doubles-as-spy.md)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface ModuleTestDouble {
}
