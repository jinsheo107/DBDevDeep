package com.dbdevdeep.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.FileService;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.domain.MySignDto;
import com.dbdevdeep.employee.service.EmployeeService;
import com.dbdevdeep.security.service.SecurityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class EmployeeApiController {

	private final EmployeeService employeeService;
	private final FileService fileService;
	private final SecurityService securityService;

	@ResponseBody
	@PostMapping("/govid")
	public Map<String, String> govIdCheck(@RequestBody String govId) {

		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "중복확인 중 오류가 발생하였습니다.");

		if (employeeService.govIdCheck(govId) == 1) {
			resultMap.put("res_code", "409");
			resultMap.put("res_msg", "중복되는 값이 존재합니다.");
		} else {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "중복되는 값이 없습니다.");
		}

		return resultMap;
	}

	@ResponseBody
	@PostMapping("/signup")
	public Map<String, String> signup(EmployeeDto dto, @RequestParam("file") MultipartFile file) {

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "계정 등록 중 오류가 발생하였습니다.");

		String savedFileName = fileService.employeePicUpload(file);

		if (savedFileName != null) {
			dto.setOri_pic_name(file.getOriginalFilename());
			dto.setNew_pic_name(savedFileName);

			if (employeeService.addEmployee(dto) > 0) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "계정 등록에 성공하였습니다.");
			}
		} else {
			resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
		}
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/addsign")
	public Map<String, String> addSign(MySignDto dto, @RequestParam("file") MultipartFile file) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "서명 등록 중 오류가 발생하였습니다.");

		String savedFileName = fileService.employeeSignPicUpload(file);

		if (savedFileName != null) {
			dto.setOri_pic_name(file.getOriginalFilename());
			dto.setNew_pic_name(savedFileName);

			if (employeeService.employeeSignAdd(dto) > 0) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "서명 등록에 성공하였습니다.");
			}
		} else {
			resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
		}
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/editsign")
	public Map<String, String> editSign(MySignDto dto, @RequestParam("file") MultipartFile file) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "서명 등록 중 오류가 발생하였습니다.");

		String savedFileName = fileService.employeeSignPicUpload(file);

		if (savedFileName != null) {
			dto.setOri_pic_name(file.getOriginalFilename());
			dto.setNew_pic_name(savedFileName);

			if (employeeService.employeeSignAdd(dto) > 0) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "서명 수정에 성공하였습니다.");
			}
		} else {
			resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
		}
		return resultMap;
	}
	
	@ResponseBody
	@DeleteMapping("/mysign/{sign_no}")
	public Map<String, String> deleteBoard(@PathVariable("sign_no") Long sign_no) {
				
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 삭제 중 오류가 발생했습니다.");
		
			
		if(employeeService.deleteSign(sign_no) > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글이 성공적으로 삭제되었습니다.");
		}
		
		return resultMap;
	}

	@ResponseBody
	@PostMapping("/edit-myinfo")
	public Map<String, String> editMyInfo(EmployeeDto dto,
			@RequestParam(name = "file", required = false) MultipartFile file, HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "정보 수정 중 오류가 발생하였습니다.");

		if (file != null && "".equals(file.getOriginalFilename()) == false) {
			String savedFileName = fileService.employeePicUpload(file);

			if (savedFileName != null) {
				dto.setOri_pic_name(file.getOriginalFilename());
				dto.setNew_pic_name(savedFileName);

				if (fileService.employeePicDelete(dto.getEmp_id()) > 0) {
					resultMap.put("res_msg", "기존 파일이 정상적으로 삭제되었습니다.");
				}
			} else {
				resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
			}
		}

		Employee e = employeeService.editMyInfo(dto);

		if (e != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "내 정보가 성공적으로 수정되었습니다.");

			Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
			if (currentAuth != null && currentAuth.getPrincipal() instanceof UserDetails) {

				// 현재 세션 무효화
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				// 새로운 세션 생성
				HttpSession newSession = request.getSession(true);

				// 기존 인증 정보로 다시 로그인 처리
				UserDetails newUserDetails = securityService.loadUserByUsername(dto.getEmp_id());
				Authentication newAuth = new UsernamePasswordAuthenticationToken(newUserDetails, dto.getEmp_pw(),
						newUserDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(newAuth);
				// 새로 생성된 세션에 인증 정보 설정
				newSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			}

		}

		return resultMap;
	}

	@ResponseBody
	@PostMapping("/edit-mypw")
	public Map<String, String> editMyPw(@RequestParam("new_emp_pw") String newEmpPw,
			@RequestParam("ori_emp_pw") String oriEmpPW, HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "정보 수정 중 오류가 발생하였습니다.");

		int result = employeeService.checkPw(oriEmpPW);

		if (result > 0) {
			Employee e = employeeService.editMyPw(newEmpPw);

			EmployeeDto dto = new EmployeeDto().toDto(e);

			if (e != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "내 정보가 성공적으로 수정되었습니다.");

				Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
				if (currentAuth != null && currentAuth.getPrincipal() instanceof UserDetails) {

					// 현재 세션 무효화
					HttpSession session = request.getSession(false);
					if (session != null) {
						session.invalidate();
					}
					// 새로운 세션 생성
					HttpSession newSession = request.getSession(true);

					// 기존 인증 정보로 다시 로그인 처리
					UserDetails newUserDetails = securityService.loadUserByUsername(dto.getEmp_id());
					Authentication newAuth = new UsernamePasswordAuthenticationToken(newUserDetails, dto.getEmp_pw(),
							newUserDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(newAuth);
					// 새로 생성된 세션에 인증 정보 설정
					newSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
				}
			}
		} else {
			resultMap.put("res_msg", "비밀번호가 일치하지 않습니다.");
		}

		return resultMap;
	}
	
	// 상태 메시지 수정
	@ResponseBody
    @PostMapping("/status/{empId}")
    public Map<String,String> updateStatus(@RequestBody EmployeeDto dto){
		Map<String,String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "게시글 삭제 중 오류가 발생했습니다.");
		
		int result = employeeService.editChatStatus(dto.getEmp_id(), dto.getChat_status_msg());
		
		if(result>0) {
			map.put("res_code", "200");
			map.put("res_msg", "상태메세지가 수정되었습니다.");
		}
		
		return map;
	}
	
	// 채팅 상대 프로필 조회
	@ResponseBody
    @GetMapping("/profile/{empId}")
	public EmployeeDto selectProfile(Model model, @PathVariable("empId") String emp_id){
		EmployeeDto dto = employeeService.selectEmployeeOne(emp_id);
		
		return dto;
	}
}
