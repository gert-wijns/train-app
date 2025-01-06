package be.gert.trainapp.sm._config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Configuration
@EnableJpaRepositories(basePackages = "be.gert.trainapp.sm")
@EntityScan(basePackages = {"be.gert.trainapp.sm", "org.springframework.modulith.events.jpa"})
public class JpaConfig {
	@Bean
	public JPAQueryFactory getJpaQueryFactory(EntityManager entityManager) {
		return new JPAQueryFactory(entityManager);
	}
}
