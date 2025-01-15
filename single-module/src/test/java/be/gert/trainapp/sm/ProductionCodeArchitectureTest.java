package be.gert.trainapp.sm;

import static com.tngtech.archunit.base.DescribedPredicate.and;
import static com.tngtech.archunit.core.domain.JavaCall.Predicates.target;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.time.temporal.Temporal;
import java.util.Arrays;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.domain.properties.HasName.Predicates;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import be.gert.trainapp.sm._shared.event.PublicEvent;

@AnalyzeClasses(packages = "be.gert.trainapp.sm", importOptions = ImportOption.DoNotIncludeTests.class)
public class ProductionCodeArchitectureTest {

	@ArchTest
	public static final ArchRule toBuilderNotAllowedOutsideTests = noClasses().should()
			.callMethodWhere(target(annotatedWith(lombok.Generated.class)).and(target(Predicates.nameMatching("toBuilder"))))
			.because("adr-003-no-builders-in-production-code.md - Using toBuilder is not allowed outside tests");

	@ArchTest
	public static final ArchRule builderNotAllowedOutsideTests = noClasses().should()
			.callMethodWhere(target(annotatedWith(lombok.Generated.class)).and(target(Predicates.nameMatching("builder"))))
			.because("adr-003-no-builders-in-production-code.md - Using builder is not allowed outside tests");

	@ArchTest
	public static ArchRule useJavaTimeWithClock = noClasses().should()
				.callMethodWhere(and(
						target(Predicates.nameMatching("now")),
						new DescribedPredicate<>("without using clock") {
							@Override
							public boolean test(JavaMethodCall javaMethodCall) {
								var target = javaMethodCall.getTarget();
								return target.getName().equals("now")
										&& target.getOwner().isAssignableTo(Temporal.class)
										&& target.getParameterTypes().isEmpty();
							}
						}))
			.because("adr-005-always-use-temporal-with-app-clock.md - " +
					"XXX.now(AppClock.clock) should be used instead " +
					"so we can test easily with fixed time.");

	@ArchTest
	public static final ArchRule localhostPackageComponentsRequiresLocalhostProfile = classes()
			.that().resideInAPackage("be.gert.trainapp.sm._localhost")
				.and(annotatedWith(Component.class))
			.should().beAnnotatedWith(new DescribedPredicate<>("") {
				@Override
				public boolean test(JavaAnnotation<?> javaAnnotation) {
					if (javaAnnotation.getRawType().isAssignableFrom(Profile.class)) {
						var value = javaAnnotation.getProperties().get("value");
						return value instanceof String[] profiles && Arrays.asList(profiles).contains("localhost");
					}
					return false;
				}
			});

	@ArchTest
	public static void eventsWithoutPublicEventShouldNotBeUsedInOtherModule(JavaClasses classes) {
		var modulePackages = classes.getPackage("be.gert.trainapp.sm")
				.getSubpackages().stream()
				.filter(p -> !p.getRelativeName().startsWith("_"))
				.toList();
		for (var modulePackage: modulePackages) {
			classes()
					.that().resideInAPackage(modulePackage.getName()+"._events")
					.and().areNotAnnotatedWith(PublicEvent.class)
					.should()
					.onlyBeAccessed()
					.byClassesThat()
					.resideInAPackage(modulePackage.getName() + ".*")
					.because("adr-001-package-structure.md - only @PublicEvent may be used by another module")
					.allowEmptyShould(true)
					.check(classes);
		}
	}
}
