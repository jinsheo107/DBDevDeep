package com.dbdevdeep.approve.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import jakarta.persistence.EntityNotFoundException;
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
	
	@Transactional
	public int deleteApprove(Long appro_no) {
	    int result = 0;
	    try {
	        // 결재 정보 가져오기
	        Approve approve = approveRepository.findById(appro_no).orElse(null);
	        if (approve == null) {
	            return result; // 결재 정보가 없으면 아무 작업도 하지 않음
	        }

	        // 관련된 휴가 요청 가져오기
	        VacationRequest vacationRequest = vacationRequestRepository.findByApprove(approve);
	        if (vacationRequest != null) {
	            // 휴가 유형 확인 및 복구할 시간 계산
	            int vacType = vacationRequest.getVacType();
	            if (vacType == 0 || vacType == 3 || vacType == 6 || vacType == 7 || vacType == 8) {
	                // 복구할 시간 계산
	                int hoursToRestore = minusVac(new VacationRequestDto().toDto(vacationRequest));

	                // 사용자 정보 가져오기
	                Employee employee = approve.getEmployee();

	                // 부서와 직급 가져오기
	                Department department = approve.getDepartment();
	                Job job = approve.getJob();

	                // 사용자의 vacation_hour 복구
	                EmployeeDto employeeDto = new EmployeeDto().toDto(employee);
	                int updatedVacationHour = employeeDto.getVacation_hour() + hoursToRestore;
	                employeeDto.setVacation_hour(Math.max(updatedVacationHour, 0)); // 0 미만으로 가지 않도록 설정

	                // 부서와 직급 설정
	                employeeDto.setDepartment(department);
	                employeeDto.setJob(job);

	                // 업데이트된 DTO를 엔티티로 변환하여 저장
	                employeeRepository.save(employeeDto.toEntity());
	            }
	        }

	        // 관련된 참조, 휴가 요청, 승인 라인, 결재 정보를 삭제
	        referenceRepository.deleteByApprove(approve);
	        vacationRequestRepository.deleteByApprove(approve);
	        approveLineRepository.deleteByApprove(approve);
	        approveRepository.deleteById(appro_no);

	        result = 1;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	// 내가 결재 요청한 목록 조회
	public List<ApproveDto> selectApproveList(String empId){
		List<Approve> approveList = approveRepository.findByTypeAndEmpId(empId);
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
	
	// 요청 받은 목록 조회
		public List<ApproveDto> selectComeApproveList(String empId){
			List<Approve> approveList = approveRepository.findByListAndEmpId(empId);
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
	
	public Map<String, Object> getApproveDetail(Long approNo) {
	    Map<String, Object> detailMap = new HashMap<>();

	    // Approve 객체 가져오기
	    Approve approve = approveRepository.findByApproNo(approNo);

	    // ApproveLine 가져오기
	    List<ApproveLine> approLineList = approveLineRepository.findByApprove(approve);

	    // 결재자와 협의자를 구분하여 리스트로 할당
	    List<ApproveLineDto> approveLineList = approLineList.stream()
	        .filter(a -> "N".equals(a.getConsultYn())) // 결재자만 필터링
	        .map(a -> ApproveLineDto.builder()
	            .emp_id(a.getEmployee().getEmpId())
	            .appro_line_name(a.getApproLineName())
	            .appro_line_order(a.getApproLineOrder())
	            .appro_line_status(a.getApproLineStatus())
	            .appro_permit_time(a.getApproPermitTime())
	            .reason_back(a.getReasonBack())
	            .consult_yn(a.getConsultYn()) 
	            .build())
	        .collect(Collectors.toList());

	    List<ApproveLineDto> consultLineList = approLineList.stream()
	        .filter(a -> "Y".equals(a.getConsultYn())) // 협의자만 필터링
	        .map(a -> ApproveLineDto.builder()
	            .emp_id(a.getEmployee().getEmpId())
	            .appro_line_name(a.getApproLineName())
	            .appro_line_order(a.getApproLineOrder())
	            .appro_line_status(a.getApproLineStatus())
	            .appro_permit_time(a.getApproPermitTime())
	            .reason_back(a.getReasonBack())
	            .consult_yn(a.getConsultYn())
	            .build())
	        .collect(Collectors.toList());

	    detailMap.put("lDto", approveLineList); // 결재자 리스트
	    detailMap.put("cDto", consultLineList); // 협의자 리스트
	    
	    // 3. Reference를 가져옵니다.
	    List<Reference> reference = referenceRepository.findByApprove(approve);
	    List<ReferenceDto> refList = reference.stream()
	        .map(r -> ReferenceDto.builder()
	            .emp_id(r.getEmployee().getEmpId())
	            .ref_name(r.getRefName())
	            .build())
	        .collect(Collectors.toList());
	    detailMap.put("rDto", refList);

	    // 4. VacationRequest를 가져옵니다.
	    VacationRequest vRequest = vacationRequestRepository.findByApprove(approve);
	    VacationRequestDto vDto = null;
	    if (vRequest != null) {
	        vDto = new VacationRequestDto().toDto(vRequest);
	    }
	    detailMap.put("vDto", vDto);

	 // ApproFile을 Approve 객체를 기준으로 조회
	    ApproFile aFile = approFileRepository.findByApprove(approve);
	    ApproFileDto fileDto = null;
	    if(aFile != null) {
	    	fileDto = new ApproFileDto().toDto(aFile);
	    }
	    detailMap.put("fDto", fileDto);
	    
	    // 6. Approve DTO 변환
	    ApproveDto aDto = new ApproveDto().toDto(approve);
	    // TempEdit이 null인지 확인하고, null이 아닌 경우에만 ApproveDto 변환을 시도
	    if (approve.getTempEdit() != null) {
	        aDto.setTemp_no(approve.getTempEdit().getTempNo());
	    } else {
	        aDto.setTemp_no(null); // TempEdit이 null일 경우
	    }

	    // 나머지 Approve 정보를 설정합니다.
	    aDto.setAppro_no(approve.getApproNo());
	    aDto.setEmp_id(approve.getEmployee().getEmpId());
	    aDto.setDept_code(approve.getDepartment().getDeptCode());
	    aDto.setJob_code(approve.getJob().getJobCode());
	    aDto.setAppro_name(approve.getApproName());
	    aDto.setAppro_time(approve.getApproTime());
	    aDto.setAppro_type(approve.getApproType());
	    aDto.setAppro_status(approve.getApproStatus());
	    aDto.setAppro_title(approve.getApproTitle());
	    aDto.setAppro_content(approve.getApproContent());
	    
	    detailMap.put("aDto", aDto);

	    return detailMap;
	}
	
	// 결재 수정
	@Transactional
	public int approUpdate(ApproveDto approveDto, List<VacationRequestDto> vacationRequestDtos, List<ApproveLineDto> approveLineDtos,
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

		    // 2. vacation_request 테이블에 저장
		    VacationRequest oriVacRequest = vacationRequestRepository.findByApprove(approve);
		    if(oriVacRequest != null) {
		    	vacationRequestRepository.delete(oriVacRequest);
		    }
		    
		    
		    VacationRequestDto oriVacDto = vacationRequestDtos.get(0);
		    VacationRequestDto newVacDto = vacationRequestDtos.get(1);
		    
		    newVacDto.setAppro_no(approveDto.getAppro_no());
		    newVacDto.setVac_yn("Y");
            VacationRequest vacationRequest = newVacDto.toEntity(approve);
            vacationRequestRepository.save(vacationRequest);
		    
		
         // 3. approve_Line 테이블에 저장
            approveLineRepository.deleteByApprove(approve);
            for (ApproveLineDto lineDto : approveLineDtos) {
                lineDto.setAppro_no(approve.getApproNo());

                // 각 ApproveLine에 맞는 Employee 객체를 가져옵니다.
                Employee lineEmployee = employeeRepository.findByempId(lineDto.getEmp_id());
                if (lineEmployee == null) {
                    continue; // 또는 오류를 처리합니다.
                }

                ApproveLine approveLine = lineDto.toEntity(approve, lineEmployee);
                approveLineRepository.save(approveLine);
            }

            // 4. reference 테이블에 저장
            referenceRepository.deleteByApprove(approve);
            for (ReferenceDto refDto : referenceDto) {
                refDto.setAppro_no(approve.getApproNo());

                // 각 Reference에 맞는 Employee 객체를 가져옵니다.
                Employee refEmployee = employeeRepository.findByempId(refDto.getEmp_id());
                if (refEmployee == null) {
                    continue; // 또는 오류를 처리합니다.
                }

                Reference reference = refDto.toEntity(approve, refEmployee);
                referenceRepository.save(reference);
            }
            
         // 5. appro_file 테이블에 저장
            if (approFileDto != null && approFileDto.getOri_file() != null && !approFileDto.getOri_file().isEmpty()) { 
                approFileDto.setAppro_no(approveDto.getAppro_no());
                ApproFile approFile = approFileDto.toEntity(approve);
                approFileRepository.save(approFile);
            }
		
         // 6. 휴가 시간 차감 로직 추가
            int vacHours = 0;

            // 특정 휴가 유형에 대해 차감 로직을 수행
            if ((oriVacDto.getVac_type() == 0 || oriVacDto.getVac_type() == 3 || oriVacDto.getVac_type() == 6 ||
                 oriVacDto.getVac_type() == 7 || oriVacDto.getVac_type() == 8) &&
                (oriVacDto.getVac_type() != newVacDto.getVac_type() ||
                 !oriVacDto.getStart_time().equals(newVacDto.getStart_time()) ||
                 !oriVacDto.getEnd_time().equals(newVacDto.getEnd_time()))) {

                // 변경 전 시간 복구
                vacHours += minusVac(oriVacDto);
            }

            // 새로운 휴가 유형에 대해 차감 로직을 수행
            if (newVacDto.getVac_type() == 0 || newVacDto.getVac_type() == 3 || newVacDto.getVac_type() == 6 ||
                newVacDto.getVac_type() == 7 || newVacDto.getVac_type() == 8) {

                // 변경 후 시간 차감
                vacHours -= minusVac(newVacDto);
            }

            // 기존 vacation_hour에서 차감
            EmployeeDto employeeDto = new EmployeeDto().toDto(employee); // Employee 객체를 DTO로 변환
            int updatedVacationHour = employeeDto.getVacation_hour() + vacHours;
            employeeDto.setVacation_hour(Math.max(updatedVacationHour, 0)); // 0 미만으로 가지 않도록 설정

            employeeDto.setDepartment(department); // Department 엔티티 설정
            employeeDto.setJob(job); // Job 엔티티 설정

            // 업데이트된 DTO를 엔티티로 변환하여 저장
            employeeRepository.save(employeeDto.toEntity());
            
			return 1;
            
	}
	
	// 결재 요청 
	@Transactional
	public int approUp(ApproveDto approveDto, VacationRequestDto vacationRequestDto, List<ApproveLineDto> approveLineDtos,
	                   List<ReferenceDto> referenceDto, ApproFileDto approFileDto, MultipartFile file) {

	    // Employee, Department, Job 정보 가져오기
	    Employee employee = employeeRepository.findByempId(approveDto.getEmp_id());
	    if (employee == null) {
	    }
	    
	    Department department = departmentRepository.findByDeptCode(approveDto.getDept_code());
	    Job job = jobRepository.findByJobCode(approveDto.getJob_code());

	    // TempEdit 처리
	    TempEdit tempEdit = null;
	    if (approveDto.getTemp_no() != null) {
	        tempEdit = tempEditRepository.findById(approveDto.getTemp_no()).orElse(null);
	    }

	    if (employee == null || department == null || job == null) {
	        // 필요한 엔티티가 없는 경우 오류 반환
	        return 0;
	    }

	    // 1. approve 테이블에 저장
	    Approve approve = approveDto.toEntity(employee, department, job, tempEdit);
	    approve = approveRepository.save(approve);
	    Long approNo = approve.getApproNo(); // 저장 후 생성된 appro_no 가져오기

	    // 2. vacation_request 테이블에 저장
	    vacationRequestDto.setAppro_no(approNo);
	    VacationRequest vacationRequest = vacationRequestDto.toEntity(approve);
	    vacationRequestRepository.save(vacationRequest);
	    
	    
	 // 3. approve_Line 테이블에 저장
	    for (ApproveLineDto lineDto : approveLineDtos) {
	        lineDto.setAppro_no(approNo); // Approve의 appro_no 설정

	        // 각 ApproveLine에 맞는 Employee 객체를 가져옵니다.
	        Employee lineEmployee = employeeRepository.findByempId(lineDto.getEmp_id());
	        if (lineEmployee == null) {
	            continue; // 또는 오류를 처리합니다.
	        }

	        ApproveLine approveLine = lineDto.toEntity(approve, lineEmployee);
	        approveLineRepository.save(approveLine);
	    }

	    // 4. reference 테이블에 저장
	    for (ReferenceDto refDto : referenceDto) {
	        refDto.setAppro_no(approNo); // Approve의 appro_no 설정

	        // 각 Reference에 맞는 Employee 객체를 가져옵니다.
	        Employee refEmployee = employeeRepository.findByempId(refDto.getEmp_id());
	        if (refEmployee == null) {
	            continue; // 또는 오류를 처리합니다.
	        }

	        Reference reference = refDto.toEntity(approve, refEmployee);
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
	    if (vacationType == 0 || vacationType == 3 || vacationType == 6 || vacationType == 7 || vacationType == 8) {
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

	
	// 휴가 시간계산 메서드
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
