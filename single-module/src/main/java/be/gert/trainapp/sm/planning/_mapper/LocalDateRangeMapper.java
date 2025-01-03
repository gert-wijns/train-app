package be.gert.trainapp.sm.planning._mapper;

import be.gert.trainapp.api.planning.generated.model.LocalDateRangeBody;
import be.gert.trainapp.sm._shared.values.LocalDateRange;

public class LocalDateRangeMapper {

	public static LocalDateRangeBody toLocalDateRangeBody(LocalDateRange value) {
		return value == null ? null: new LocalDateRangeBody()
				.start(value.startDate())
				.end(value.endDate());
	}

	public static LocalDateRange toLocalDateRange(LocalDateRangeBody value) {
		return value == null ? null: new LocalDateRange(
				value.getStart(),
				value.getEnd());
	}
}
