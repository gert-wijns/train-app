package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm.personnel._mapper.FullNameMapper.toFullNameBody;
import static be.gert.trainapp.sm.personnel._model.QEmployee.employee;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.gert.trainapp.api.personnel.generated.SearchEmployeesQueryApi;
import be.gert.trainapp.api.personnel.generated.model.EmployeeRole;
import be.gert.trainapp.api.personnel.generated.model.SearchEmployeesQueryResponseItem;
import be.gert.trainapp.sm.personnel.EmployeeId;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class SearchEmployeesQuery implements SearchEmployeesQueryApi {
	private final JPAQueryFactory queryFactory;

	@Override
	public ResponseEntity<List<SearchEmployeesQueryResponseItem>> query(List<String> employeeId) {
		var query = queryFactory.from(employee)
				.select(employee.id.id, employee.fullName, employee.role)
				.orderBy(employee.fullName.firstName.toLowerCase().asc())
				.orderBy(employee.fullName.lastName.toLowerCase().asc());
		if (isNotEmpty(employeeId)) {
			query.where(employee.id.in(employeeId.stream().map(EmployeeId::new).toList()));
		}

		return ok(query.fetch().stream().map(this::toResponseItem).toList());
	}

	private SearchEmployeesQueryResponseItem toResponseItem(Tuple tuple) {
		return new SearchEmployeesQueryResponseItem()
				.id(tuple.get(employee.id.id))
				.fullName(toFullNameBody(tuple.get(employee.fullName)))
				.role(EmployeeRole.fromValue(tuple.get(employee.role).name()));
	}
}
