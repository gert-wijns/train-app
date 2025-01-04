package be.gert.trainapp.sm;

import static com.tngtech.archunit.core.domain.JavaCall.Predicates.target;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.properties.HasName.Predicates;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "be.gert.trainapp.sm", importOptions = ImportOption.DoNotIncludeTests.class)
public class ProductionCodeArchitectureTest {

	@ArchTest
	public static final ArchRule toBuilderNotAllowedOutsideTests = noClasses().should()
			.callMethodWhere(target(annotatedWith(lombok.Generated.class)).and(target(Predicates.nameMatching("toBuilder"))))
			.because("adr-002-no-builders-in-production-code.md - Using toBuilder is not allowed outside tests");

	@ArchTest
	public static final ArchRule builderNotAllowedOutsideTests = noClasses().should()
			.callMethodWhere(target(annotatedWith(lombok.Generated.class)).and(target(Predicates.nameMatching("builder"))))
			.because("adr-002-no-builders-in-production-code.md - Using builder is not allowed outside tests");

}
