package com.dbdevdeep.approve.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import com.dbdevdeep.employee.domain.Department;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.Job;

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
public class ApproveDto {

	private Long appro_no;
	private String emp_id;
	private Long temp_no;
	private String dept_code;
	private String job_code;
	private String appro_name;
	private LocalDateTime appro_time;
	private int appro_type;
	private int appro_status;
	private String appro_title;
	private String appro_content;
	
	private Integer vac_type;
	private int search_type = 1;
	private String search_text;
	
	public Approve toEntity(Employee employee, Department department, Job job, TempEdit tempEdit) {
		return Approve.builder()
				.approNo(appro_no)
				.employee(employee)
				.tempEdit(tempEdit)
				.department(department)
				.job(job)
				.approName(appro_name)
				.approTime(appro_time)
				.approType(appro_type)
				.approStatus(appro_status)
				.approTitle(appro_title)
				.approContent(appro_content)
				.build();
	}
	
	public ApproveDto toDto(Approve approve) {
        // TempEdit이 null인 경우 안전하게 처리합니다.
        Long tempNo = Optional.ofNullable(approve.getTempEdit()).map(TempEdit::getTempNo).orElse(null);

        return ApproveDto.builder()
                .appro_no(approve.getApproNo())
                .emp_id(approve.getEmployee().getEmpId())
                .temp_no(tempNo)  // TempEdit의 temp_no 설정
                .dept_code(approve.getDepartment().getDeptCode())
                .job_code(approve.getJob().getJobCode())
                .appro_name(approve.getApproName())
                .appro_time(approve.getApproTime())
                .appro_type(approve.getApproType())
                .appro_status(approve.getApproStatus())
                .appro_title(approve.getApproTitle())
                .appro_content(approve.getApproContent())
                .build();
    }
	
	 public ApproveDto toDto(Approve approve , Integer vacType) {
	        // TempEdit이 null인 경우 안전하게 처리합니다.
	        Long tempNo = Optional.ofNullable(approve.getTempEdit()).map(TempEdit::getTempNo).orElse(null);

	        return ApproveDto.builder()
	                .appro_no(approve.getApproNo())
	                .emp_id(approve.getEmployee().getEmpId())
	                .temp_no(tempNo)  // TempEdit의 temp_no 설정
	                .dept_code(approve.getDepartment().getDeptCode())
	                .job_code(approve.getJob().getJobCode())
	                .appro_name(approve.getApproName())
	                .appro_time(approve.getApproTime())
	                .appro_type(approve.getApproType())
	                .appro_status(approve.getApproStatus())
	                .appro_title(approve.getApproTitle())
	                .appro_content(approve.getApproContent())
	                .vac_type(vacType) // 휴가 유형 설정
	                .build();
	    }
	}