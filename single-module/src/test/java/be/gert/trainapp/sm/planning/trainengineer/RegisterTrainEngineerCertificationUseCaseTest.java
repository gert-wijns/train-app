package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm._shared.clock.AppClock.clock;
import static be.gert.trainapp.sm.personnel.given.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.planning.given.CertificationDefaults.certificateCodeTsiLoc;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.LocalDateRangeBody;
import be.gert.trainapp.api.planning.generated.model.RegisterTrainEngineerCertificationRequest;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm.TestEntities;
import be.gert.trainapp.sm._shared.values.LocalDateRange;
import be.gert.trainapp.sm.planning.trainengineer.model.TrainEngineer;
import be.gert.trainapp.sm.planning.trainengineer.model.TrainEngineerCertification;
import be.gert.trainapp.sm.planning.trainengineer.model.TrainEngineerCertificationId;

@ModuleTest
class RegisterTrainEngineerCertificationUseCaseTest {
	@Autowired
	TestEntities testEntities;
	@Autowired
	RegisterTrainEngineerCertificationUseCase usecase;

	TrainEngineerCertification trainEngineerCertification = new TrainEngineerCertification(
			new TrainEngineerCertificationId(employeeChristineGonzalesId, certificateCodeTsiLoc),
			new LocalDateRange(
					LocalDate.now(clock),
					LocalDate.now(clock).plusMonths(6)));
	TrainEngineer trainEngineer = new TrainEngineer(
			employeeChristineGonzalesId,
			false,
			List.of(trainEngineerCertification));

	RegisterTrainEngineerCertificationRequest request =
			new RegisterTrainEngineerCertificationRequest()
					.employeeId(employeeChristineGonzalesId.id())
					.certificateCode(certificateCodeTsiLoc.code())
					.certificationPeriod(new LocalDateRangeBody()
							.start(trainEngineerCertification.validRange().startDate())
							.end(trainEngineerCertification.validRange().endDate()));

	@Test
	void success() {
		usecase.execute(request);

		testEntities.assertState(trainEngineer);
	}
}
