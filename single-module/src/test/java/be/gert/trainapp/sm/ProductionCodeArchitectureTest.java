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
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.domain.properties.HasName.Predicates;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

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
			.should().beAnnotatedWith(Profile.class)
			.andShould(new ArchCondition<>("") {
				@Override
				public void check(JavaClass item, ConditionEvents events) {
					boolean satisfied = Arrays.stream(item.getAnnotationOfType(Profile.class).value())
							.anyMatch(profile -> profile.equals("localhost"));
					if (!satisfied) {
						events.add(SimpleConditionEvent.violated(item,
								String.format("Class %s has is in _localhost but lacks @Profiles(\"localhost\")",
										item.getFullName())));
					}
				}
			});
}
