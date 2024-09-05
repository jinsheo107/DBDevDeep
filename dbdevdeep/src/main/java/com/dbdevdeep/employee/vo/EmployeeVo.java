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
	private String empRrn;
	private String empPhone;
	private String oriPicName;
	private String newPicName;
	private String empPostCode;
	private String empAddr;
	private String empDetailAddr;
	private String deptCode;
	private String jobCode;
	private String empInternalPhone;
	private int vacationHour;
	private LocalDate hireDate;
	private LocalDate endDate;
	private String entStatus;
	private String loginYn;
	private String accountStatus;
	private String chatStatusMsg;
}
