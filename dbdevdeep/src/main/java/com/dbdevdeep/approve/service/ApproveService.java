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
import com.dbdevdeep.approve.domain.VacationRequest;
import com.dbdevdeep.approve.domain.VacationRequestDto;
import com.dbdevdeep.approve.repository.ApproFileRepository;
import com.dbdevdeep.approve.repository.ApproveLineRepository;
import com.dbdevdeep.approve.repository.ApproveRepository;
import com.dbdevdeep.approve.repository.ReferenceRepository;
import com.dbdevdeep.approve.repository.VacationRequestRepository;
import com.dbdevdeep.employee.domain.Department;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.domain.Job;
import com.dbdevdeep.employee.repository.EmployeeRepository;

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
	
	@Autowired
	public ApproveService(ApproveRepository approveRepository, 
            VacationRequestRepository vacationRequestRepository,
            ApproveLineRepository approveLineRepository, 
            ApproFileRepository approFileRepository,
            ReferenceRepository referenceRepository,
            EmployeeRepository employeeRepository,
            FileService fileService) {
		this.approveRepository = approveRepository;
		this.vacationRequestRepository = vacationRequestRepository;
        this.approveLineRepository = approveLineRepository;
        this.approFileRepository = approFileRepository;
        this.referenceRepository = referenceRepository;
        this.employeeRepository = employeeRepository;
        this.fileService = fileService;
	}
	
	public List<ApproveDto> selectApproveList(String empId){
		List<Approve> approveList = approveRepository.findByEmployeeEmpId(empId);
		
		List<ApproveDto> approveDtoList = new ArrayList<ApproveDto>();
		for(Approve a : approveList) {
			ApproveDto dto = ApproveDto.builder()
					.appro_no(a.getApproNo())
					.emp_id(a.getEmployee().getEmpId())
					.temp_no(a.getTempEdit().getTempNo())
					.dept_code(a.getDepartment().getDeptCode())
					.job_code(a.getJob().getJobCode())
					.appro_time(a.getApproTime())
					.appro_type(a.getApproType())
					.appro_status(a.getApproStatus())
					.appro_title(a.getApproTitle())
					.appro_content(a.getApproContent())
					.build();
			
			approveDtoList.add(dto);
			System.out.println(approveDtoList);
		}
		return approveDtoList;
	}
	
	@Transactional
	public int approUp(ApproveDto approveDto, VacationRequestDto vacationRequestDto, List<ApproveLineDto> approveLineDtos,
			List<ReferenceDto> referenceDto , ApproFileDto approFileDto, MultipartFile file) {
		
		
			// 1. approve 테이블에 저장
			Approve approve = approveDto.toEntity();
            approve = approveRepository.save(approve);
            
            // 저장 후 생성된 appro_no 가져오기
            Long approNo = approve.getApproNo();
            
            // 2. vacation_request 테이블에 저장
            VacationRequest vacationRequest = vacationRequestDto.toEntity();
            // vacationRequest.setApprove(approve); // Approve와 연관관계 설정
            vacationRequestRepository.save(vacationRequest);
            
            // 3. approve_Line 테이블에 저장
            for (ApproveLineDto lineDto : approveLineDtos) {
                lineDto.setAppro_no(approNo); // Approve의 appro_no 설정
                ApproveLine approveLine = lineDto.toEntity();
              //  approveLine.setApprove(approve); // Approve와 연관관계 설정
                approveLineRepository.save(approveLine);
            }
            
            // 4. reference 테이블에 저장
            for (ReferenceDto refDto : referenceDto) {
                refDto.setAppro_no(approNo); // Approve의 appro_no 설정
                Reference reference = refDto.toEntity();
              //  reference.setApprove(approve); // Approve와 연관관계 설정
                referenceRepository.save(reference);
            }
            
            // 5. appro_file 테이블에 저장
            approFileDto.setAppro_no(approNo); // Approve의 appro_no 설정
            ApproFile approFile = approFileDto.toEntity();
           // approFile.setApprove(approve); // Approve와 연관관계 설정
            approFileRepository.save(approFile);
            
            // 6. 직원 휴가 시간 차감
            Employee employee = employeeRepository.findByempId(approveDto.getEmp_id());
            if (employee != null) {
            	int deductedHours = minusVac(vacationRequestDto);
            	EmployeeDto employeeDto = new EmployeeDto();
            	employeeDto.setVacation_hour(employee.getVacationHour() - minusVac(vacationRequestDto));
            	Employee updateVac = employeeDto.toEntity();
            	employeeRepository.save(updateVac);
            } else {
                return 0; // 직원 정보가 없을 경우 실패 반환
            }
            
            return 1; // 성공시 1 반환
            
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
