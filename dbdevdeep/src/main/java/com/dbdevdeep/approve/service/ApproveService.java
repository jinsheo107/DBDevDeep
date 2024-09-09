package com.dbdevdeep.approve.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.FileService;
import com.dbdevdeep.approve.domain.ApproFile;
import com.dbdevdeep.approve.domain.ApproFileDto;
import com.dbdevdeep.approve.domain.Approve;
import com.dbdevdeep.approve.domain.ApproveDto;
import com.dbdevdeep.approve.domain.ApproveLine;
import com.dbdevdeep.approve.domain.ApproveLineDto;
import com.dbdevdeep.approve.domain.Reference;
import com.dbdevdeep.approve.domain.ReferenceDto;
import com.dbdevdeep.approve.domain.TempEdit;
import com.dbdevdeep.approve.domain.VacationRequest;
import com.dbdevdeep.approve.domain.VacationRequestDto;
import com.dbdevdeep.approve.repository.ApproFileRepository;
import com.dbdevdeep.approve.repository.ApproveLineRepository;
import com.dbdevdeep.approve.repository.ApproveRepository;
import com.dbdevdeep.approve.repository.ReferenceRepository;
import com.dbdevdeep.approve.repository.TempEditRepository;
import com.dbdevdeep.approve.repository.VacationRequestRepository;
import com.dbdevdeep.employee.domain.Department;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.domain.Job;
import com.dbdevdeep.employee.repository.DepartmentRepository;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.employee.repository.JobRepository;

import jakarta.transaction.Transactional;

@Service
public class ApproveService {

	private final ApproveRepository approveRepository;
	private final VacationRequestRepository vacationRequestRepository;
    private final ApproveLineRepository approveLineRepository;
    private final ApproFileRepository approFileRepository;
    private final ReferenceRepository referenceRepository;
    private final EmployeeRepository employeeRepository;
    private final FileService fileService;
	private final DepartmentRepository departmentRepository;
	private final JobRepository jobRepository;
	private final TempEditRepository tempEditRepository;
	
	@Autowired
	public ApproveService(ApproveRepository approveRepository, 
            VacationRequestRepository vacationRequestRepository,
            ApproveLineRepository approveLineRepository, 
            ApproFileRepository approFileRepository,
            ReferenceRepository referenceRepository,
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            JobRepository jobRepository,
            TempEditRepository tempEditRepository,
            FileService fileService) {
		this.approveRepository = approveRepository;
		this.vacationRequestRepository = vacationRequestRepository;
        this.approveLineRepository = approveLineRepository;
        this.approFileRepository = approFileRepository;
        this.referenceRepository = referenceRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.jobRepository = jobRepository;
        this.tempEditRepository = tempEditRepository;
        this.fileService = fileService;
	}
	
	public List<ApproveDto> selectApproveList(String empId){
		List<Approve> approveList = approveRepository.findByEmployeeEmpId(empId);
		List<ApproveDto> approveDtoList = new ArrayList<ApproveDto>();
		
		for(Approve a : approveList) {
			
		
			ApproveDto dto = ApproveDto.builder()
					.appro_no(a.getApproNo())
					.emp_id(a.getEmployee().getEmpId())
					.dept_code(a.getDepartment().getDeptCode())
					.job_code(a.getJob().getJobCode())
					.appro_time(a.getApproTime())
					.appro_type(a.getApproType())
					.appro_status(a.getApproStatus())
					.appro_title(a.getApproTitle())
					.appro_content(a.getApproContent())
					.build();
			
			approveDtoList.add(dto);
		}
		return approveDtoList;
	}
	
	@Transactional
	public int approUp(ApproveDto approveDto, VacationRequestDto vacationRequestDto, List<ApproveLineDto> approveLineDtos,
			List<ReferenceDto> referenceDto , ApproFileDto approFileDto, MultipartFile file) {

		Employee employee = employeeRepository.findByempId(approveDto.getEmp_id());
	    Department department = departmentRepository.findByDeptCode(approveDto.getDept_code());
	    Job job = jobRepository.findByJobCode(approveDto.getJob_code());
		
	    TempEdit tempEdit = null;
	    if (approveDto.getTemp_no() != null) {  // temp_no가 null이 아닐 때만 findById를 호출합니다.
	        tempEdit = tempEditRepository.findById(approveDto.getTemp_no()).orElse(null);
	    }
	    
	    if (employee == null || department == null || job == null) {
	        // 필요한 엔티티가 없는 경우 예외를 던지거나 오류를 처리합니다.
	        return 0;
	    }
	    
			// 1. approve 테이블에 저장
		    Approve approve = approveDto.toEntity(employee, department, job, tempEdit);
		    approve = approveRepository.save(approve);
            
            // 저장 후 생성된 appro_no 가져오기
            Long approNo = approve.getApproNo();
            
            // 2. vacation_request 테이블에 저장
            vacationRequestDto.setAppro_no(approNo);
            VacationRequest vacationRequest = vacationRequestDto.toEntity(approve);
            // vacationRequest.setApprove(approve); // Approve와 연관관계 설정
            vacationRequestRepository.save(vacationRequest);
            
            // 3. approve_Line 테이블에 저장
            for (ApproveLineDto lineDto : approveLineDtos) {
                lineDto.setAppro_no(approNo); // Approve의 appro_no 설정
                ApproveLine approveLine = lineDto.toEntity(approve, employee);
              //  approveLine.setApprove(approve); // Approve와 연관관계 설정
                approveLineRepository.save(approveLine);
            }
            
            // 4. reference 테이블에 저장
            for (ReferenceDto refDto : referenceDto) {
                refDto.setAppro_no(approNo); // Approve의 appro_no 설정
                Reference reference = refDto.toEntity(approve, employee);
              //  reference.setApprove(approve); // Approve와 연관관계 설정
                referenceRepository.save(reference);
            }
            
         // 5. appro_file 테이블에 저장
            if (approFileDto != null) { // approFileDto가 null이 아닐 때만 저장
                approFileDto.setAppro_no(approNo); // Approve의 appro_no 설정
                ApproFile approFile = approFileDto.toEntity(approve);
                approFileRepository.save(approFile);
            }
            
            // 6. 휴가 시간 차감 로직 추가
            int vacationType = vacationRequestDto.getVac_type();
            if (vacationType == 0 || vacationType == 1 || vacationType == 7 || vacationType == 8 || vacationType == 4) {
                // 사용자가 선택한 휴가 유형에 따른 차감 시간 계산
                int hoursToDeduct = minusVac(vacationRequestDto);

                // 기존 vacation_hour에서 차감
                EmployeeDto employeeDto = new EmployeeDto().toDto(employee); // Employee 객체를 DTO로 변환
                int updatedVacationHour = employeeDto.getVacation_hour() - hoursToDeduct;
                employeeDto.setVacation_hour(Math.max(updatedVacationHour, 0)); // 0 미만으로 가지 않도록 설정
                
                employeeDto.setDepartment(department); // Department 엔티티 설정
                employeeDto.setJob(job); // Job 엔티티 설정

                // 업데이트된 DTO를 엔티티로 변환하여 저장
                employeeRepository.save(employeeDto.toEntity());
            }
			return 1;
            
	}
	
	private int minusVac(VacationRequestDto vacationRequestDto) {
	    LocalDateTime startDate = vacationRequestDto.getStart_time();
	    LocalDateTime endDate = vacationRequestDto.getEnd_time();

	    // 두 날짜의 연도, 월, 일이 동일한 경우
	    if (startDate.toLocalDate().equals(endDate.toLocalDate())) {
	        // 시작과 끝의 시간이 동일하면 하루(8시간)
	        if (startDate.toLocalTime().equals(endDate.toLocalTime())) {
	            return 8;
	        } else {
	            // 시간이 다르면, 시간 차이만큼 차감
	            return endDate.getHour() - startDate.getHour();
	        }
	    } else {
	        // 날짜가 다를 경우 전체 일수 * 8 시간 계산
	        long daysBetween = java.time.Duration.between(startDate.toLocalDate().atStartOfDay(), endDate.toLocalDate().atStartOfDay()).toDays() + 1; // 포함된 일 수
	        return (int) (daysBetween * 8);
	    }
	}

}
