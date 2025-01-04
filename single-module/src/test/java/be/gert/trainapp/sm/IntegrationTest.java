package be.gert.trainapp.sm;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
@SpringBootTest
public @interface IntegrationTest {
}
