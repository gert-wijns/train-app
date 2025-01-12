package be.gert.trainapp.sm.planning._model;

import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.planning._model.TrainEngineerCertificationDefaults.trainEngineerCertification;

import java.util.ArrayList;
import java.util.List;

public class TrainEngineerDefaults {

	public static TrainEngineer trainEngineer() {
		return new TrainEngineer(
				employeeChristineGonzalesId,
				true,
				new ArrayList<>(List.of(trainEngineerCertification())));
	}
}
