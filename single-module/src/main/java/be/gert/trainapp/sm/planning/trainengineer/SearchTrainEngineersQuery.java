package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.planning.trainengineer.model.QTrainEngineer.trainEngineer;
import static be.gert.trainapp.sm.planning.trainengineer.model.QTrainEngineerCertification.trainEngineerCertification;
import static com.querydsl.jpa.JPAExpressions.select;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.springframework.http.ResponseEntity.ok;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.planning.generated.SearchTrainEngineersQueryApi;
import be.gert.trainapp.api.planning.generated.model.SearchTrainEngineersQueryResponseItem;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.planning._model.CertificateCode;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class SearchTrainEngineersQuery implements SearchTrainEngineersQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchTrainEngineersQueryResponseItem>> query(
			List<String> certificationCodes,
			LocalDate certificationValidFrom,
			LocalDate certificationValidUntil) {
		var query = queryFactory.from(trainEngineer)
				.select(trainEngineer.id);
		if (isNotEmpty(certificationCodes)) {
			var certIds = certificationCodes.stream().map(CertificateCode::new).toList();
			var matchCount = select(trainEngineerCertification.id.count())
					.where(trainEngineerCertification.id.employeeId.eq(trainEngineer.id))
					.where(trainEngineerCertification.id.certificateCode.in(certIds))
					.where(trainEngineerCertification.validRange.startDate.after(certificationValidFrom).not())
					.where(trainEngineerCertification.validRange.endDate.before(certificationValidUntil).not())
					.from(trainEngineerCertification);
			query.where(matchCount.eq((long) certificationCodes.size()));
		}
		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchTrainEngineersQueryResponseItem toResponseItem(EmployeeId employeeId) {
		return new SearchTrainEngineersQueryResponseItem()
				.id(employeeId.id());
	}
}
