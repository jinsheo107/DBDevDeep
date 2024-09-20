package com.dbdevdeep.employee.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GradeClassRequest {

	private String t_year;
	private int grade_1;
	private int grade_2;
	private int grade_3;
	private int grade_4;
	private int grade_5;
	private int grade_6;
}
