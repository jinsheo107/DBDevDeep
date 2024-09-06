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

	private String emp_id;
	private String emp_pw;
	private String gov_id;
	private String emp_name;
	private String emp_rrn;
	private String emp_phone;
	private String ori_pic_name;
	private String new_pic_name;
	private String emp_post_code;
	private String emp_addr;
	private String emp_detail_addr;
	
	private String dept_code;
	private String job_code;
	
	private String emp_internal_phone;
	private int vacation_hour;
	private LocalDate hire_date;
	private LocalDate end_date;
	private String ent_status;
	private String login_yn;
	private String account_status;
	private String chat_status_msg;
}
