package com.dbdevdeep.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.employee.domain.TeacherHistoryDto;
import com.dbdevdeep.employee.service.TeacherHistoryService;
import com.dbdevdeep.student.domain.StudentClassDto;
import com.dbdevdeep.student.domain.StudentDto;
import com.dbdevdeep.student.service.StudentFileService;
import com.dbdevdeep.student.service.StudentService;

@Controller
public class StudentApiController {
	
	// 의존성 주입
	private final StudentFileService studentFileService;
	private final StudentService studentService;
	private final TeacherHistoryService teacherHistoryService;
	
	@Autowired
	public StudentApiController(StudentService studentService, StudentFileService studentFileService, TeacherHistoryService teacherHistoryService) {
		this.studentService = studentService;
		this.studentFileService = studentFileService;
		this.teacherHistoryService = teacherHistoryService;
	}
	
	// 게시글 등록
	@ResponseBody
	@PostMapping("/student")
	public Map<String,String> createStudent(StudentDto dto,
	        @RequestParam("file") MultipartFile file){
	    Map<String,String> resultMap = new HashMap<String,String>();
	    resultMap.put("res_code", "404");
	    resultMap.put("res_msg", "게시글 등록 중 오류가 발생했습니다.");
	    
	    try {
	        // 파일이 null이 아닌 경우 처리
	        if(file != null && !file.isEmpty()) {  
	            String originalFilename = file.getOriginalFilename();
	            
	            // 파일 이름이 비어있거나 null인지 확인
	            if (originalFilename != null && !originalFilename.isEmpty()) {
	                String savedFileName = studentFileService.upload(file);
	                
	                if(savedFileName != null) {
	                    dto.setStudent_ori_pic(originalFilename);
	                    dto.setStudent_new_pic(savedFileName);
	                }
	            } else {
	                resultMap.put("res_msg", "파일 이름이 유효하지 않습니다.");
	                return resultMap;
	            }
	        }
	        // 학생 정보 생성
	        if(studentService.createStudent(dto) != null) {
	            resultMap.put("res_code", "200");
	            resultMap.put("res_msg", "게시글이 성공적으로 등록되었습니다.");
	        } else {
	            resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
	        }
	    } catch (Exception e) {
	        // 예외 발생 시 메시지 처리
	       e.printStackTrace();
	    }
	    
	    return resultMap;
	}
	@ResponseBody
	@PostMapping("/student/{student_no}")
	public Map<String,String> updateStudent(StudentDto dto,
			@RequestParam(name="file",required=false)MultipartFile file){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 수정 중 오류가 발생했습니다.");
		
		if(file != null && "".equals(file.getOriginalFilename()) == false) {
			String savedFileName = studentFileService.upload(file);
			if(savedFileName != null) {
				dto.setStudent_ori_pic(file.getOriginalFilename());
				dto.setStudent_new_pic(savedFileName);
				
				if(studentFileService.delete(dto.getStudent_no()) > 0){
					resultMap.put("res_msg", "기존 파일이 정상적으로 삭제되었습니다");
				}
				
			}else {
				resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
			}
		}
		
		if(studentService.updateStudentInfo(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글이 성공적으로 수정되었습니다.");
		}
		
		return resultMap;
	}
	
	// 삭제 처리
	@ResponseBody
	@DeleteMapping("/student/{student_no}")
	public Map<String,String> deleteStudent(@PathVariable("student_no") Long student_no){
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "게시글 삭제 중 오류가 발생했습니다");
		
		if(studentFileService.delete(student_no) > 0) {
			map.put("res_msg","기존 파일이 정상적으로 삭제되었습니다.");
			if(studentService.deleteStudent(student_no)>0) {				
				map.put("res_code", "200");
				map.put("res_msg","정상적으로 게시글이 삭제되었습니다.");
			}
		}
		return map;
	}
	
	// 반배정시 학년도에 따른 학년 데이터 가져오기
	@GetMapping("/student/student_class/selectByYear/{t_year}")
	@ResponseBody
	public List<TeacherHistoryDto> getDataByYear(@PathVariable("t_year") String t_year) {
		// 학년도에 해당하는 데이터를 가져옵니다.
	    List<TeacherHistoryDto> data = teacherHistoryService.selectClassByYearList(t_year);
	    // 데이터를 JSON 형식으로 반환합니다.
	    return data;
	}
	
	@GetMapping("/student/student_class/selectByYearAndGrade/{t_year}/{grade}")
	@ResponseBody
	public List<TeacherHistoryDto> getDataByYearAndGrade(@PathVariable("t_year") String t_year, @PathVariable("grade") String grade) {
	    // t_year와 grade에 해당하는 데이터를 조회
	    List<TeacherHistoryDto> data = teacherHistoryService.getDataByYearAndGradeList(t_year, grade);
	    List<TeacherHistoryDto> filteredData = data.stream()
                .filter(Objects::nonNull) // null이 아닌 객체만 필터링
                .collect(Collectors.toList());

	    return filteredData;
	}
	
	@ResponseBody
	@PostMapping("/student/class/assign")
	public Map<String,String> assignClassForStudent(StudentClassDto dto){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "반 배정 중 오류가 발생했습니다.");
		
		if(studentService.assignClass(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "반 배정이 성공적으로 수행되었습니다.");
		}
		return resultMap;
	}
}
