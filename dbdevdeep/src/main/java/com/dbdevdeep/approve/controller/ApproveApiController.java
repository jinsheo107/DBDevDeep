package com.dbdevdeep.approve.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.FileService;
import com.dbdevdeep.approve.domain.ApproFileDto;
import com.dbdevdeep.approve.domain.ApproveDto;
import com.dbdevdeep.approve.domain.ApproveLineDto;
import com.dbdevdeep.approve.domain.ReferenceDto;
import com.dbdevdeep.approve.domain.VacationRequestDto;
import com.dbdevdeep.approve.service.ApproveService;
import com.dbdevdeep.employee.domain.EmployeeDto;

@Controller
public class ApproveApiController {

	private final ApproveService approveService;
	private final FileService fileService;
	
	private String pullId(String input) {
	    return input.substring(input.indexOf('(') + 1, input.indexOf(')'));
	}
	
	
	
	@Autowired
	public ApproveApiController(ApproveService approveService, FileService fileService) {
		this.approveService = approveService;
		this.fileService = fileService;
	}
	
	
	@ResponseBody
	@PostMapping("/approUp")
	public Map<String, String> approUp(
			@RequestParam("emp_id") String empId,
		    @RequestParam("dept_code") String deptCode,
		    @RequestParam("job_code") String jobCode,
		    @RequestParam("appro_title") String approTitle,
		    @RequestParam("appro_content") String approContent,
		    @RequestParam("vac_type") int vacType,
		    @RequestParam("start_date") String startDate,
		    @RequestParam("end_date") String endDate,
		    @RequestParam("consult") String consult,
		    @RequestParam("approval") String approval,
		    @RequestParam("reference") String reference,
		    @RequestParam("file_name") MultipartFile file) {
		
		System.out.println("잡코드"+jobCode);
		
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "결재 요청 중 오류가 발생하였습니다.");
		
		try {
			
			
		ApproveDto approveDto = new ApproveDto();
			approveDto.setEmp_id(empId);
			approveDto.setDept_code(deptCode);
			approveDto.setJob_code(jobCode);
			approveDto.setAppro_title(approTitle);
			approveDto.setAppro_content(approContent);
			
		VacationRequestDto vacationRequestDto = new VacationRequestDto();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDate startDateLocal = LocalDate.parse(startDate, formatter);
			LocalDate endDateLocal = LocalDate.parse(endDate, formatter);

			// LocalDate를 LocalDateTime으로 변환 (자정 시간으로 설정)
			LocalDateTime startDateTime = startDateLocal.atStartOfDay();
			LocalDateTime endDateTime = endDateLocal.atStartOfDay();

			vacationRequestDto.setVac_yn("Y");
			vacationRequestDto.setVac_type(vacType);
			vacationRequestDto.setStart_time(startDateTime);
			vacationRequestDto.setEnd_time(endDateTime);
			
		List<ApproveLineDto> approveLineDtos = new ArrayList<>();
		LocalDateTime currentTime = LocalDateTime.now();
		
		if(consult !=null && !consult.isEmpty()) {
			String[] consults = consult.split(">");
			int order = 1;
			for(String c : consults) {
				String consultId = pullId(c);
				approveLineDtos.add(new ApproveLineDto(null, null, consultId, order++, 0, currentTime ,null, "Y"));
			}
		}
		
		if(approval != null && !approval.isEmpty()) {
			String[] approvals = approval.split(">");
			int order = (consult != null ? consult.split(">").length + 1 : 1);
			for(String a : approvals) {
				String approvalId = pullId(a);
				approveLineDtos.add(new ApproveLineDto(null, null, approvalId , order++ , 0 , currentTime ,null, "Y"));
			}
		}
		
		List<ReferenceDto> referenceDto = new ArrayList<>();
			if(reference != null && !reference.isEmpty()) {
				String[] references = reference.split(">");
				for(String r : references) {
					String refId = pullId(r);
					referenceDto.add(new ReferenceDto(null , null , refId));
				}
			}
			
		// 파일 업로드 설정
			ApproFileDto approFileDto = new ApproFileDto();
		    if (file != null && !file.isEmpty()) {
		        // 파일 저장 및 정보 설정
		        String savedFileName = fileService.employeePicUpload(file);
		        if (savedFileName != null) {
		            approFileDto.setNew_file(savedFileName);
		            approFileDto.setOri_file(file.getOriginalFilename());
		        } else {
		            resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
		            return resultMap; 
		        }
		    }
		
		
			int result = approveService.approUp(approveDto, vacationRequestDto, approveLineDtos, referenceDto ,approFileDto, file);
			
			if(result > 0) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "결재요청 하였습니다.");
			}
		} catch (Exception e) {
	        e.printStackTrace();  // 서버 로그에 오류 출력
	        resultMap.put("res_msg", "서버 내부 오류: " + e.getMessage());
	    }
		
		return resultMap;
	}
	
}
