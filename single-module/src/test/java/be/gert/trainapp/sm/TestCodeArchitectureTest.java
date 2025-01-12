package be.gert.trainapp.sm;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.springframework.context.annotation.Configuration;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import be.gert.trainapp.sm._shared.testdoubles.ModuleTestDouble;

@AnalyzeClasses(packages = "be.gert.trainapp.sm", importOptions = ImportOption.OnlyIncludeTests.class)
public class TestCodeArchitectureTest {

	@ArchTest
	public static final ArchRule testDoublesMustHaveModuleTestDoubleAnnotation = classes().that()
			.resideInAPackage("be.gert.trainapp.sm.*._testdoubles").and().areNotAnnotatedWith(Configuration.class)
			.should().beAnnotatedWith(ModuleTestDouble.class)
			.because("all test doubles should be annotated with ModuleTestDouble to facilitate adr-011-test-doubles-as-spy.md");

}
