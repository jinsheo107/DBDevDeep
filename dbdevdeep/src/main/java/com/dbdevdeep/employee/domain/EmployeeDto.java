package com.dbdevdeep.employee.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

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
public class EmployeeDto {
	private String emp_id;
	private String emp_pw;
	private String gov_id;
	private String emp_name;
	private String emp_no;
	private String emp_phone;
	private String ori_pic;
	private String new_pic;
	private String emp_post;
	private String emp_addr;
	private String emp_detail_addr;
	private String dept_code;
	private String job_code;
	private String emp_internal_phone;
	private int vacation_time;
	private LocalDate hire_date;
	private LocalDate end_date;
	private String ent_status;
	private String login_yn;
	private String account_status;
	private String chat_status_msg;
	
	private int search_type = 1;
	private String search_text;
	
	// GrantedAuthority: 시큐리티에서 권한들의 목록을 담아놓는 곳
	private List<GrantedAuthority> authorities;
	
	public Employee toEntity() {
		return Employee.builder()
				.empId(emp_id).empPw(emp_pw).govId(gov_id)
				.empName(emp_name).empNo(emp_no).empPhone(emp_phone)
				.oriPic(ori_pic).newPic(new_pic).empPost(emp_post)
				.empAddr(emp_addr).empDetailAddr(emp_detail_addr).deptCode(dept_code)
				.jobCode(job_code).empInternalPhone(emp_internal_phone).vacationTime(vacation_time)
				.hireDate(hire_date).endDate(end_date).entStatus(ent_status)
				.loginYn(login_yn).accountStatus(account_status).chatStatusMsg(chat_status_msg).build();
				
	}
	
	public EmployeeDto toDto(Employee employee) {
		return EmployeeDto.builder()
				.emp_id(employee.getEmpId()).emp_pw(employee.getEmpPw())
				.gov_id(employee.getGovId()).emp_name(employee.getEmpName())
				.emp_no(employee.getEmpNo()).emp_phone(employee.getEmpPhone())
				.ori_pic(employee.getOriPic()).new_pic(employee.getNewPic())
				.emp_post(employee.getEmpPost()).emp_addr(employee.getEmpAddr())
				.emp_detail_addr(employee.getEmpDetailAddr())
				.dept_code(employee.getDeptCode()).job_code(employee.getJobCode())
				.emp_internal_phone(employee.getEmpInternalPhone())
				.vacation_time(employee.getVacationTime())
				.hire_date(employee.getHireDate()).end_date(employee.getEndDate())
				.ent_status(employee.getEntStatus()).login_yn(employee.getLoginYn())
				.account_status(employee.getAccountStatus())
				.chat_status_msg(employee.getChatStatusMsg()).build();
	}
	
}
