package be.gert.trainapp.sm._localhost;

import static be.gert.trainapp.sm._localhost.CertificateDataLoader.certificateCodeNntrFrance;
import static be.gert.trainapp.sm._localhost.CertificateDataLoader.certificateCodeTsiLoc;
import static be.gert.trainapp.sm._localhost.CertificateDataLoader.certificateCodeTsiWag;
import static be.gert.trainapp.sm._localhost.EmployeeDataLoader.employeeChristinaId;
import static be.gert.trainapp.sm._localhost.EmployeeDataLoader.employeeHannahId;
import static be.gert.trainapp.sm._localhost.EmployeeDataLoader.employeeJohnId;

import java.time.LocalDate;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.planning.generated.model.LocalDateRangeBody;
import be.gert.trainapp.api.planning.generated.model.RegisterTrainEngineerCertificationRequest;
import be.gert.trainapp.sm._shared.clock.AppClock;
import be.gert.trainapp.sm.planning.trainengineer.RegisterTrainEngineerCertificationUseCase;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class TrainEngineerDataLoader {
	private final RegisterTrainEngineerCertificationUseCase registerUsecase;

	public void loadTrainEngineers() {
		registerUsecase.execute(new RegisterTrainEngineerCertificationRequest()
				.employeeId(employeeHannahId.id())
				.certificateCode(certificateCodeTsiLoc.code())
				.certificationPeriod(new LocalDateRangeBody()
						.start(LocalDate.now(AppClock.clock))
						.end(LocalDate.now(AppClock.clock).plusMonths(6))));

		registerUsecase.execute(new RegisterTrainEngineerCertificationRequest()
				.employeeId(employeeChristinaId.id())
				.certificateCode(certificateCodeNntrFrance.code())
				.certificationPeriod(new LocalDateRangeBody()
						.start(LocalDate.now(AppClock.clock))
						.end(LocalDate.now(AppClock.clock).plusMonths(6))));

		registerUsecase.execute(new RegisterTrainEngineerCertificationRequest()
				.employeeId(employeeJohnId.id())
				.certificateCode(certificateCodeTsiWag.code())
				.certificationPeriod(new LocalDateRangeBody()
						.start(LocalDate.now(AppClock.clock))
						.end(LocalDate.now(AppClock.clock).plusMonths(6))));
	}
}
