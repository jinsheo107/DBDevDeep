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
	private String emp_rrn;
	private String emp_phone;
	private String ori_pic_name;
	private String new_pic_name;
	private String emp_post_code;
	private String emp_addr;
	private String emp_detail_addr;
	
	// department join
	private String dept_code;
	private String dept_name;
	
	// job join
	private String job_code;
	private String job_name;
	
	private String emp_internal_phone;
	private int vacation_hour;
	private LocalDate hire_date;
	private LocalDate end_date;
	private String ent_status;
	private String login_yn;
	private String account_status;
	private String chat_status_msg;
	
	// GrantedAuthority: 시큐리티에서 권한들의 목록을 담아놓는 곳
	private List<GrantedAuthority> authorities;
	
	public Employee toEntity() {
		return Employee.builder()
				.empId(emp_id).empPw(emp_pw).govId(gov_id)
				.empName(emp_name).empRrn(emp_rrn).empPhone(emp_phone)
				.oriPicName(ori_pic_name).newPicName(new_pic_name).empPostCode(emp_post_code)
				.empAddr(emp_addr).empDetailAddr(emp_detail_addr)
				.empInternalPhone(emp_internal_phone).vacationHour(vacation_hour)
				.hireDate(hire_date).endDate(end_date).entStatus(ent_status)
				.loginYn(login_yn).accountStatus(account_status).chatStatusMsg(chat_status_msg)
				.build();
				
	}
	
	public EmployeeDto toDto(Employee employee) {
		return EmployeeDto.builder()
				.emp_id(employee.getEmpId()).emp_pw(employee.getEmpPw())
				.gov_id(employee.getGovId()).emp_name(employee.getEmpName())
				.emp_rrn(employee.getEmpRrn()).emp_phone(employee.getEmpPhone())
				.ori_pic_name(employee.getOriPicName()).new_pic_name(employee.getNewPicName())
				.emp_post_code(employee.getEmpPostCode()).emp_addr(employee.getEmpAddr())
				.emp_detail_addr(employee.getEmpDetailAddr())
				.emp_internal_phone(employee.getEmpInternalPhone())
				.vacation_hour(employee.getVacationHour())
				.hire_date(employee.getHireDate()).end_date(employee.getEndDate())
				.ent_status(employee.getEntStatus()).login_yn(employee.getLoginYn())
				.account_status(employee.getAccountStatus())
				.chat_status_msg(employee.getChatStatusMsg())
				.job_code(employee.getJob().getJobCode())
				.job_name(employee.getJob().getJobName())
				.dept_code(employee.getDepartment().getDeptCode())
				.dept_name(employee.getDepartment().getDeptName())
				.build();
	}
	
}
