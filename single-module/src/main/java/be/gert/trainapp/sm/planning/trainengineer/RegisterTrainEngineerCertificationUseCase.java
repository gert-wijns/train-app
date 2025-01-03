package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.planning._mapper.LocalDateRangeMapper.toLocalDateRange;
import static be.gert.trainapp.sm.planning.trainengineer.model.TrainEngineer.newTrainEngineer;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.planning.generated.RegisterTrainEngineerCertificationUseCaseApi;
import be.gert.trainapp.api.planning.generated.model.RegisterTrainEngineerCertificationRequest;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning.certification.CertificateCode;
import be.gert.trainapp.sm.planning.trainengineer.jpa.TrainEngineerJpaRepository;
import be.gert.trainapp.sm.planning.trainengineer.model.TrainEngineer;
import be.gert.trainapp.sm.planning.trainengineer.model.TrainEngineerCertification;
import be.gert.trainapp.sm.planning.trainengineer.model.TrainEngineerCertificationId;
import be.gert.trainapp.sm.planning.trainengineer.model.events.TrainEngineerCertificationRegistered;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class RegisterTrainEngineerCertificationUseCase implements RegisterTrainEngineerCertificationUseCaseApi {
	private final TrainEngineerJpaRepository jpa;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public ResponseEntity<Void> execute(RegisterTrainEngineerCertificationRequest request) {
		var employeeId = new EmployeeId(request.getEmployeeId());
		var trainEngineer = findOrCreateTrainEngineer(employeeId);

		var certificateCode = new CertificateCode(request.getCertificateCode());
		var certification = new TrainEngineerCertification(
				new TrainEngineerCertificationId(employeeId, certificateCode),
				toLocalDateRange(request.getCertificationPeriod()));
		trainEngineer.certifications().add(certification);
		jpa.save(trainEngineer);

		eventPublisher.publishEvent(new TrainEngineerCertificationRegistered(employeeId, certificateCode));
		return noContent().build();
	}

	private TrainEngineer findOrCreateTrainEngineer(EmployeeId id) {
		return jpa.findById(id).orElseGet(() -> jpa.save(newTrainEngineer(id)));
	}
}
