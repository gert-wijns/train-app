package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm._shared.clock.AppClock.clock;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzales;
import static be.gert.trainapp.sm.planning._model.CertificationDefaults.certificateCodeTsiLoc;
import static be.gert.trainapp.sm.planning._model.CertificationDefaults.certificateCodeTsiWag;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.planning.generated.model.SearchTrainEngineersQueryResponseItem;
import be.gert.trainapp.sm.ModuleTest;
import be.gert.trainapp.sm._shared.values.LocalDateRange;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning._model.CertificateCode;
import be.gert.trainapp.sm.planning._model.TrainEngineer;
import be.gert.trainapp.sm.planning._model.TrainEngineerCertification;
import be.gert.trainapp.sm.planning._model.TrainEngineerCertificationId;
import be.gert.trainapp.sm.planning._repository.TrainEngineerJpaRepository;
import lombok.With;

@ModuleTest
class SearchTrainEngineersQueryTest {
	@Autowired
	TrainEngineerJpaRepository jpa;
	@Autowired
	SearchTrainEngineersQuery query;

	@With
	record TestFilterInput(
			List<String> certificationCodes,
			LocalDate certificationValidFrom,
			LocalDate certificationValidUntil
	) {
	}

	static EmployeeId employeeId = employeeChristineGonzales().id();
	static LocalDate tsiLocStart = now(clock);
	static LocalDate tsiLocEnd = now(clock).plusMonths(6);
	static LocalDate tsiWagStart = now(clock);
	static LocalDate tsiWagEnd = now(clock).plusWeeks(3);

	@BeforeEach
	void setup() {
		// given driver with certificates
		var tsiLoc = certificate(certificateCodeTsiLoc, tsiLocStart, tsiLocEnd);
		var tsiWag = certificate(certificateCodeTsiWag, tsiWagStart, tsiWagEnd);
		jpa.save(TrainEngineer.builder()
				.id(employeeId)
				.certifications(List.of(tsiLoc, tsiWag))
				.build());
	}

	@ParameterizedTest
	@MethodSource("findsResultWhenSearchParamsMatchInput")
	void findsResultWhenSearchParamsMatch(TestFilterInput filter) {
		// when
		var result = query.query(filter.certificationCodes, filter.certificationValidFrom, filter.certificationValidUntil).getBody();

		// then
		assertThat(result).containsExactly(new SearchTrainEngineersQueryResponseItem().id(employeeId.id()));
	}

	private static Stream<TestFilterInput> findsResultWhenSearchParamsMatchInput() {
		var empty = new TestFilterInput(null, null, null);

		return Stream.of(
				empty,
				empty.withCertificationCodes(List.of(certificateCodeTsiLoc.code()))
						.withCertificationValidFrom(tsiLocStart)
						.withCertificationValidUntil(tsiLocEnd),
				empty.withCertificationCodes(List.of(certificateCodeTsiLoc.code(), certificateCodeTsiWag.code()))
						.withCertificationValidFrom(tsiWagStart.plusDays(1))
						.withCertificationValidUntil(tsiWagEnd.minusDays(1))
		);
	}

	@ParameterizedTest
	@MethodSource("findsNoResultWhenSearchParamsNotMatchInput")
	void findsNoResultWhenSearchParamsNotMatch(TestFilterInput filter) {
		// when
		var result = query.query(filter.certificationCodes, filter.certificationValidFrom, filter.certificationValidUntil).getBody();

		// then
		assertThat(result).isEmpty();
	}

	private static Stream<TestFilterInput> findsNoResultWhenSearchParamsNotMatchInput() {
		var empty = new TestFilterInput(null, null, null);

		return Stream.of(
				empty.withCertificationCodes(List.of("?"))
						.withCertificationValidFrom(tsiLocStart)
						.withCertificationValidUntil(tsiLocEnd),
				empty.withCertificationCodes(List.of(certificateCodeTsiLoc.code()))
						.withCertificationValidFrom(tsiLocStart.minusDays(1))
						.withCertificationValidUntil(tsiLocEnd),
				empty.withCertificationCodes(List.of(certificateCodeTsiLoc.code(), certificateCodeTsiWag.code()))
						.withCertificationValidFrom(tsiLocStart.plusDays(1))
						.withCertificationValidUntil(tsiWagEnd.plusWeeks(4))
		);
	}

	private TrainEngineerCertification certificate(CertificateCode code, LocalDate start, LocalDate end) {
		return new TrainEngineerCertification(new TrainEngineerCertificationId(employeeChristineGonzales().id(), code), new LocalDateRange(start, end));
	}

}
