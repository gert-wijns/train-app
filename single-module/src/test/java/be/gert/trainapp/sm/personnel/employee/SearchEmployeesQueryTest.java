package be.gert.trainapp.sm.personnel.employee;

import static be.gert.trainapp.sm.ValidationAssertionDefaults.assertValid;
import static be.gert.trainapp.sm.personnel._model.EmployeeDefaults.employeeChristineGonzales;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.personnel.generated.model.SearchEmployeesQueryResponseItem;
import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel._repository.EmployeeJpaRepository;
import lombok.Data;
import lombok.experimental.Accessors;

@ModuleCoreTest
class SearchEmployeesQueryTest {
	@Autowired
	EmployeeJpaRepository jpa;
	@Autowired
	SearchEmployeesQuery query;

	static EmployeeId employee1Id = EmployeeId.newEmployeeId();
	static EmployeeId employee2Id = EmployeeId.newEmployeeId();

	@Data @Accessors(chain = true, fluent = true)
	static class TestFilterInput {
		List<EmployeeId> filterEmployeeIds;
		List<EmployeeId> expectedEmployeeIds;
	}

	@BeforeEach
	void setup() {
		jpa.save(employeeChristineGonzales().toBuilder().id(employee1Id).build());
		jpa.save(employeeChristineGonzales().toBuilder().id(employee2Id).build());
	}

	@ParameterizedTest
	@MethodSource("testFilterInputs")
	void testFilter(TestFilterInput filter) {
		// when
		var result = query.query(filter.filterEmployeeIds.stream().map(EmployeeId::id).toList());

		// then
		assertValid(result);
		assertThat(result)
				.map(SearchEmployeesQueryResponseItem::getId)
				.map(EmployeeId::new)
				.containsExactlyInAnyOrderElementsOf(filter.expectedEmployeeIds());
	}

	private static Stream<TestFilterInput> testFilterInputs() {
		return Stream.of(
				new TestFilterInput()
						.filterEmployeeIds(List.of())
						.expectedEmployeeIds(List.of(employee1Id, employee2Id)),
				new TestFilterInput()
						.filterEmployeeIds(List.of(employee1Id, employee2Id))
						.expectedEmployeeIds(List.of(employee1Id, employee2Id)),
				new TestFilterInput()
						.filterEmployeeIds(List.of(employee1Id, EmployeeId.newEmployeeId()))
						.expectedEmployeeIds(List.of(employee1Id))
		);
	}
}
