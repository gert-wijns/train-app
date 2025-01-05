package be.gert.trainapp.sm;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.RecordApplicationEvents;

import jakarta.transaction.Transactional;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
@SpringBootTest
@ComponentScan
@ActiveProfiles("ModuleTest")
@Transactional
@EnableJpaAuditing
@RecordApplicationEvents
@Import(ModuleTestAutowireCandidateResolverConfigurer.class)
public @interface ModuleTest {
}
