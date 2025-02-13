package be.gert.trainapp.sm;

import java.time.Clock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulith;
import org.springframework.scheduling.annotation.EnableAsync;

import be.gert.trainapp.sm._shared.clock.AppClock;

@EnableAsync
@Modulith(sharedModules = "_shared")
//@SpringBootApplication
public class TrainAppApplication {

	public static void main(String[] args) {
		AppClock.clock = Clock.systemDefaultZone();
		SpringApplication.run(TrainAppApplication.class, args);
	}

}
