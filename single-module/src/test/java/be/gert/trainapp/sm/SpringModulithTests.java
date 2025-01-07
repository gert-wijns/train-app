package be.gert.trainapp.sm;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

import com.tngtech.archunit.core.domain.JavaClass;

import be.gert.trainapp.sm._localhost.LocalDataLoader;


class SpringModulithTests {

   ApplicationModules modules = ApplicationModules.of(TrainAppApplication.class,
           JavaClass.Predicates.resideInAPackage(LocalDataLoader.class.getPackageName()));

   @Test
   void shouldBeCompliant() {
      modules.verify();
   }
}
