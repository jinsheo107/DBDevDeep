package com.dbdevdeep.approve.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	// 직원 아이디 추출
	private String pullId(String input) {
	    return input.substring(input.indexOf('(') + 1, input.indexOf(')'));
	}
	// 직원 직급과 이름 추출
	private String pullName(String input) {
	    return input.substring(0, input.indexOf('(')).trim();
	}
	
	// String 시간 변환 메서드
	private LocalDateTime changeTime(String dateStr) {
	    try {
	        // datetime-local 형식으로 파싱 시도
	        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	        return LocalDateTime.parse(dateStr, dateTimeFormatter);
	    } catch (DateTimeParseException e) {
	        // datetime-local 형식이 아니면 date 형식으로 파싱
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate localDate = LocalDate.parse(dateStr, dateFormatter);
	        return localDate.atStartOfDay();
	    }
	}
	
	
	@Autowired
	public ApproveApiController(ApproveService approveService, FileService fileService) {
		this.approveService = approveService;
		this.fileService = fileService;
	}
	
	// 결재 삭제
	@ResponseBody
	@DeleteMapping("/appro/{appro_no}")
	public Map<String, String> deleteAppro(@PathVariable("appro_no") Long appro_no) {
	    Map<String, String> map = new HashMap<>();
	    map.put("res_code", "404");
	    map.put("res_msg", "삭제 중 오류가 발생하였습니다.");

	    int fileDeleteResult = fileService.approFileDelete(appro_no);

	    if (fileDeleteResult >= 0) { 
	        if (approveService.deleteApprove(appro_no) > 0) {
	            map.put("res_code", "200");
	            map.put("res_msg", "정상적으로 삭제되었습니다.");
	        } else {
	            map.put("res_msg", "승인 정보 삭제에 실패하였습니다.");
	        }
	    } else {
	        map.put("res_msg", "파일 삭제 중 오류가 발생하였습니다.");
	    }
	    return map;
	}
	
	// 결재 수정
	@ResponseBody
	@PostMapping("/approReUp/{appro_no}")
	public Map<String,String> updateApprove(
			@RequestParam("appro_no") Long approNo,
			@RequestParam("appro_name") String approName,
			@RequestParam("dept_code") String deptCode,
			@RequestParam("emp_id") String empId,
			@RequestParam("job_code") String jobCode,
			@RequestParam("appro_title") String approTitle,
			// 변경 전 휴가 종류
			@RequestParam("vac_type") int vacType,
			// 변경 후 휴가 종류
			@RequestParam("vac_type_display") int vacTypeDisplay ,
			// 수정된 일정
			@RequestParam("start_date") String startDate,
			@RequestParam("end_date") String endDate,
			// 수정전 일정
			@RequestParam("start_date_ori") String startDateOri,
			@RequestParam("end_date_ori") String endDateOri,
			@RequestParam("appro_content") String approContent,
			@RequestParam("consult") String consult,
			@RequestParam("approval") String approval,
			@RequestParam("reference") String reference,
			@RequestParam(name="file_name", required=false)MultipartFile file){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 수정중 오류가 발생했습니다.");
		
		ApproFileDto approFileDto = new ApproFileDto();
		if(file != null && "".equals(approFileDto.getOri_file()) == false) {
			String savedFileName = fileService.approveUpload(file);
			if(savedFileName != null) {
				approFileDto.setOri_file(approFileDto.getOri_file());
				approFileDto.setNew_file(savedFileName);
				if(fileService.approFileDelete(approNo) > 0) {
					resultMap.put("res_msg", "기존 파일이 정상적으로 삭제되었습니다.");
				}
			} else {
				resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
			}
		}
		
		
		try {
			ApproveDto approveDto = new ApproveDto();
			approveDto.setAppro_no(approNo);
			approveDto.setEmp_id(empId);
			approveDto.setDept_code(deptCode);
			approveDto.setJob_code(jobCode);
			approveDto.setAppro_name(approName);
			approveDto.setAppro_title(approTitle);
			approveDto.setAppro_content(approContent);
			
			List<VacationRequestDto> vacationRequestDtos = new ArrayList<>();
			// 변경 전 휴가 타입과 시간
			VacationRequestDto prevVacationRequestDto = new VacationRequestDto();
		    prevVacationRequestDto.setVac_type(vacType);
		    prevVacationRequestDto.setStart_time(changeTime(startDateOri));
		    prevVacationRequestDto.setEnd_time(changeTime(endDateOri));
		    vacationRequestDtos.add(prevVacationRequestDto);

		    // 변경 후 휴가 타입과 시간
		    VacationRequestDto newVacationRequestDto = new VacationRequestDto();
		    newVacationRequestDto.setVac_type(vacTypeDisplay);
		    newVacationRequestDto.setStart_time(changeTime(startDate));
		    newVacationRequestDto.setEnd_time(changeTime(endDate));
		    vacationRequestDtos.add(newVacationRequestDto);

			List<ApproveLineDto> approveLineDtos = new ArrayList<>();
			LocalDateTime currentTime = LocalDateTime.now();
			int order = 1;
			boolean firstSet = false;
			
			if(consult !=null && !consult.isEmpty()) {
				String[] consults = consult.split(">");
				for(String c : consults) {
					String consultId = pullId(c);
					String consultName = pullName(c);
					int status = firstSet ? 0 : 1;
					firstSet = true;
					approveLineDtos.add(new ApproveLineDto(null, null, consultId, consultName ,order++, status, currentTime ,null, "Y"));
				}
			}
			if(approval != null && !approval.isEmpty()) {
				String[] approvals = approval.split(">");
				for(String a : approvals) {
					String approvalId = pullId(a);
					String approvalName = pullName(a);
					int status = firstSet ? 0 : 1;
					firstSet = true;
					approveLineDtos.add(new ApproveLineDto(null, null, approvalId , approvalName ,order++ , status , currentTime ,null, "N"));
				}
			}
			
			List<ReferenceDto> referenceDto = new ArrayList<>();
				if(reference != null && !reference.isEmpty()) {
					String[] references = reference.split(">");
					for(String r : references) {
						String refId = pullId(r);
						String refName = pullName(r);
						referenceDto.add(new ReferenceDto(null , null , refId , refName));
					}
				}
			
			int result = approveService.approUpdate(approveDto, vacationRequestDtos, approveLineDtos, referenceDto ,approFileDto, file);
			
			if(result > 0) {
				resultMap.put("res_code","200");
				resultMap.put("res_msg", "게시글이 성공적으로 수정되었습니다.");
			}
		} catch (Exception e) {
	        e.printStackTrace();  // 서버 로그에 오류 출력
	        resultMap.put("res_msg", "서버 내부 오류: " + e.getMessage());
	    }
		
		return resultMap;
	}
		
	
	// 결재 요청
	@ResponseBody
	@PostMapping("/approUp")
	public Map<String, String> approUp(
			@RequestParam("emp_name") String approName,
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
		
		
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "결재 요청 중 오류가 발생하였습니다.");
		
		try {
			
		ApproveDto approveDto = new ApproveDto();
			approveDto.setEmp_id(empId);
			approveDto.setDept_code(deptCode);
			approveDto.setJob_code(jobCode);
			approveDto.setAppro_name(approName);
			approveDto.setAppro_title(approTitle);
			approveDto.setAppro_content(approContent);
			
		VacationRequestDto vacationRequestDto = new VacationRequestDto();
			LocalDateTime startDateTime;
			LocalDateTime endDateTime;

			try {
				// datetime-local 형식으로 파싱 시도
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
				startDateTime = LocalDateTime.parse(startDate, dateTimeFormatter);
				endDateTime = LocalDateTime.parse(endDate, dateTimeFormatter);
			} catch (DateTimeParseException e) {
				// datetime-local 형식이 아니면 date 형식으로 파싱
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate startDateLocal = LocalDate.parse(startDate, dateFormatter);
				LocalDate endDateLocal = LocalDate.parse(endDate, dateFormatter);
				startDateTime = startDateLocal.atStartOfDay();
				endDateTime = endDateLocal.atStartOfDay();
			}

			vacationRequestDto.setVac_yn("Y");
			vacationRequestDto.setVac_type(vacType);
			vacationRequestDto.setStart_time(startDateTime);
			vacationRequestDto.setEnd_time(endDateTime);
			
		List<ApproveLineDto> approveLineDtos = new ArrayList<>();
		LocalDateTime currentTime = LocalDateTime.now();
		int order = 1;
		boolean firstSet = false;
		
		if(consult !=null && !consult.isEmpty()) {
			String[] consults = consult.split(">");
			for(String c : consults) {
				String consultId = pullId(c);
				String consultName = pullName(c);
				int status = firstSet ? 0 : 1;
				firstSet = true;
				approveLineDtos.add(new ApproveLineDto(null, null, consultId, consultName ,order++, status, currentTime ,null, "Y"));
			}
		}
		
		if(approval != null && !approval.isEmpty()) {
			String[] approvals = approval.split(">");
			for(String a : approvals) {
				String approvalId = pullId(a);
				String approvalName = pullName(a);
				int status = firstSet ? 0 : 1;
				firstSet = true;
				approveLineDtos.add(new ApproveLineDto(null, null, approvalId , approvalName ,order++ , status , currentTime ,null, "N"));
			}
		}
		
		List<ReferenceDto> referenceDto = new ArrayList<>();
			if(reference != null && !reference.isEmpty()) {
				String[] references = reference.split(">");
				for(String r : references) {
					String refId = pullId(r);
					String refName = pullName(r);
					referenceDto.add(new ReferenceDto(null , null , refId , refName));
				}
			}
			
		// 파일 업로드 설정
			ApproFileDto approFileDto = null; // 파일이 없는 경우 null로 설정 
			if (file != null && !file.isEmpty()) {
			    // 파일 저장 및 정보 설정
			    String savedFileName = fileService.approveUpload(file);
			    if (savedFileName != null) {
			        approFileDto = new ApproFileDto(); // 파일이 있을 경우에만 객체 생성
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
