package com.dbdevdeep.employee.domain;

import java.time.LocalDate;
import java.util.List;

import com.dbdevdeep.approve.domain.Approve;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employee")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Employee {
	
	@Id
	@Column(name="emp_id")
	private String empId;
	
	@Column(name="emp_pw")
	private String empPw;
	
	@Column(name="gov_id")
	private String govId;
	
	@Column(name="emp_name")
	private String empName;
	
	@Column(name="emp_no")
	private String empNo;
	
	@Column(name="emp_phone")
	private String empPhone;
	
	@Column(name="ori_pic")
	private String oriPic;
	
	@Column(name="new_pic")
	private String newPic;
	
	@Column(name="emp_post")
	private String empPost;
	
	@Column(name="emp_addr")
	private String empAddr;
	
	@Column(name="emp_detail_addr")
	private String empDetailAddr;
	
	@ManyToOne
	@JoinColumn(name="dept_code")
	private Department department;
	
	@ManyToOne
	@JoinColumn(name="job_code")
	private Job job;
	
	@Column(name="emp_internal_phone")
	private String empInternalPhone;
	
	@Column(name="vacation_time")
	private int vacationTime;
	
	@Column(name="hire_date")
	private LocalDate hireDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column(name="ent_status")
	private String entStatus;
	
	@Column(name="login_yn")
	private String loginYn;	
	
	@Column(name="account_status")
	private String accountStatus;
	
	@Column(name="chat_status_msg")
	private String chatStatusMsg;
	
	@OneToMany(mappedBy = "employee")
	private List<TeacherHistory> teacherHistorys;
	
}
