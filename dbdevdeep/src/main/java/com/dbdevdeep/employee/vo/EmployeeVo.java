package com.dbdevdeep.employee.vo;

import java.time.LocalDate;

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
public class EmployeeVo {

	private String empId;
	private String empPw;
	private String govId;
	private String empName;
	private String empNo;
	private String empPhone;
	private String oriPic;
	private String newPic;
	private String empPost;
	private String empAddr;
	private String empDetailAddr;
	private String dept_code;
	private String job_code;
	private String empInternalPhone;
	private int vacationTime;
	private LocalDate hireDate;
	private LocalDate endDate;
	private String entStatus;
	private String loginYn;
	private String accountStatus;
	private String chatStatusMsg;
}
