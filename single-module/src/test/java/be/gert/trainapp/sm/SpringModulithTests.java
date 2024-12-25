package be.gert.trainapp.sm;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

import com.tngtech.archunit.core.domain.JavaClass;


class SpringModulithTests {

   ApplicationModules modules = ApplicationModules.of(TrainAppApplication.class, JavaClass.Predicates.type(LocalDataLoader.class));

   @Test
   void shouldBeCompliant() {
      modules.verify();
   }
}
