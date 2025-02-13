package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzalesId;
import static be.gert.trainapp.sm.planning._model.CertificationDefaults.certificateCodeTsiLoc;
import static be.gert.trainapp.sm.planning._model.TrainEngineerCertificationDefaults.trainEngineerCertificationValidityPeriod;
import static be.gert.trainapp.sm.planning._model.TrainEngineerDefaults.trainEngineer;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.LocalDateRangeBody;
import be.gert.trainapp.api.planning.generated.model.SearchTrainEngineersDetailQueryCertificationItemResponse;
import be.gert.trainapp.api.planning.generated.model.SearchTrainEngineersDetailQueryResponse;
import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.planning._repository.TrainEngineerJpaRepository;

@ModuleCoreTest
class SearchTrainEngineersDetailQueryTest {
	@Autowired
	TrainEngineerJpaRepository jpa;
	@Autowired
	SearchTrainEngineersDetailQuery query;

	@Test
	void selects() {
		// given
		jpa.save(trainEngineer());

		// when
		var result = query.query(employeeChristineGonzalesId.id());

		// then
		assertValid(result);
		assertThat(result)
				.isEqualTo(new SearchTrainEngineersDetailQueryResponse()
						.id(employeeChristineGonzalesId.id())
						.active(true)
						.certifications(List.of(
								new SearchTrainEngineersDetailQueryCertificationItemResponse()
										.certificateCode(certificateCodeTsiLoc.code())
										.certificationPeriod(new LocalDateRangeBody()
												.start(trainEngineerCertificationValidityPeriod.startDate())
												.end(trainEngineerCertificationValidityPeriod.endDate()))
						)));
	}
}
