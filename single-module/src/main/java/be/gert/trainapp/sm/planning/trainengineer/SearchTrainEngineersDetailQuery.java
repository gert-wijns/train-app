package be.gert.trainapp.sm.planning.trainengineer;

import static be.gert.trainapp.sm.planning._mapper.LocalDateRangeMapper.toLocalDateRangeBody;
import static be.gert.trainapp.sm.planning._model.QTrainEngineer.trainEngineer;
import static be.gert.trainapp.sm.planning._model.QTrainEngineerCertification.trainEngineerCertification;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.planning.generated.SearchTrainEngineersDetailQueryApi;
import be.gert.trainapp.api.planning.generated.model.SearchTrainEngineersDetailQueryCertificationItemResponse;
import be.gert.trainapp.api.planning.generated.model.SearchTrainEngineersDetailQueryResponse;
import be.gert.trainapp.sm._shared.query.DomainQuery;
import be.gert.trainapp.sm.personnel.EmployeeId;
import lombok.RequiredArgsConstructor;

@DomainQuery
@RequiredArgsConstructor
public class SearchTrainEngineersDetailQuery implements SearchTrainEngineersDetailQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public SearchTrainEngineersDetailQueryResponse query(String employeeIdStr) {
		var employeeId = new EmployeeId(employeeIdStr);
		var certifications = fetchCertifications(employeeId);
		var query = queryFactory.from(trainEngineer)
				.select(trainEngineer.id.id, trainEngineer.active)
				.where(trainEngineer.id.eq(employeeId));
		return toResponse(query.fetch().getFirst())
				.certifications(certifications);
	}

	private List<SearchTrainEngineersDetailQueryCertificationItemResponse> fetchCertifications(EmployeeId employeeId) {
		var query = queryFactory.from(trainEngineerCertification)
				.select(trainEngineerCertification.id.certificateCode.code,
						trainEngineerCertification.validRange)
				.where(trainEngineerCertification.id.employeeId.eq(employeeId));
		return query.fetch().stream().map(this::toCertificateResponse).toList();
	}

	private SearchTrainEngineersDetailQueryCertificationItemResponse toCertificateResponse(Tuple tuple) {
		return new SearchTrainEngineersDetailQueryCertificationItemResponse()
				.certificateCode(tuple.get(trainEngineerCertification.id.certificateCode.code))
				.certificationPeriod(toLocalDateRangeBody(tuple.get(trainEngineerCertification.validRange)));
	}

	private SearchTrainEngineersDetailQueryResponse toResponse(Tuple tuple) {
		return new SearchTrainEngineersDetailQueryResponse()
				.id(tuple.get(trainEngineer.id.id))
				.active(tuple.get(trainEngineer.active));
	}
}
