package com.dbdevdeep.employee.domain;

import java.time.LocalDate;
import java.util.List;

import com.dbdevdeep.approve.domain.ApproDraft;
import com.dbdevdeep.approve.domain.Approve;
import com.dbdevdeep.approve.domain.ApproveLine;
import com.dbdevdeep.approve.domain.Attendance;
import com.dbdevdeep.approve.domain.LineDraft;
import com.dbdevdeep.approve.domain.Reference;
import com.dbdevdeep.approve.domain.ReferenceDraft;
import com.dbdevdeep.schedule.domain.Schedule;

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
	
	@Column(name="emp_rrn")
	private String empRrn;
	
	@Column(name="emp_phone")
	private String empPhone;
	
	@Column(name="ori_pic_name")
	private String oriPicName;
	
	@Column(name="new_pic_name")
	private String newPicName;
	
	@Column(name="emp_post_code")
	private String empPostCode;
	
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
	
	@Column(name="vacation_hour")
	private int vacationHour;
	
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

	@OneToMany(mappedBy = "employee")
	private List<Schedule> shcedules;

	@OneToMany(mappedBy = "employee")
    private List<Approve> approves;

    @OneToMany(mappedBy = "employee")
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "employee")
    private List<ApproveLine> approveLines;

    @OneToMany(mappedBy = "employee")
    private List<Reference> references;

    @OneToMany(mappedBy = "employee")
    private List<ApproDraft> approveDrafts;

    @OneToMany(mappedBy = "employee")
    private List<ReferenceDraft> referenceDrafts;

    @OneToMany(mappedBy = "employee")
    private List<LineDraft> lineDrafts;
    
}