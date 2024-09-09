package com.dbdevdeep.student.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TimeTableDto {
	private Long timetable_no;
	private Long subject_no;
	private String period;
	private String day;
	private String timetable_class;
	
	public TimeTable toEntity() {
		return TimeTable.builder()
				.timetableNo(timetable_no)
				.period(period)
				.day(day)
				.timetableClass(timetable_class)
				.build();
	}
	
	public TimeTableDto toDto(TimeTable tt) {
		return TimeTableDto.builder()
				.timetable_no(tt.getTimetableNo())
				.period(tt.getPeriod())
				.day(tt.getDay())
				.timetable_class(tt.getTimetableClass())
				.build();
	}
}
