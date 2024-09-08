package com.dbdevdeep.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.employee.domain.TeacherHistoryDto;
import com.dbdevdeep.employee.service.TeacherHistoryService;
import com.dbdevdeep.employee.vo.GradeClassRequest;

@Controller
public class TeacherHistoryApiController {

	private final TeacherHistoryService teacherHistoryService;

	@Autowired
	public TeacherHistoryApiController(TeacherHistoryService teacherHistoryService) {
		this.teacherHistoryService = teacherHistoryService;
	}

	@ResponseBody
	@PostMapping("/grade-class")
	public Map<String, String> createGradeClasses(@RequestBody GradeClassRequest gcr) {
		
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "반 생성 중 오류가 발생하였습니다.");
		try {
			if (teacherHistoryService.tYearCheck(gcr.getT_year()) == 1) {
				resultMap.put("res_code", "409");
				resultMap.put("res_msg", gcr.getT_year() + "년도의 반 정보가 있습니다.");
			} else {
				if (gcr.getGrade_1() > 0) {
					for (int i = 1; i <= gcr.getGrade_1(); i++) {
						teacherHistoryService.saveTeacherHistory(1, i, gcr.getT_year());
					}
				}

				if (gcr.getGrade_2() > 0) {
					for (int i = 1; i <= gcr.getGrade_2(); i++) {
						teacherHistoryService.saveTeacherHistory(2, i, gcr.getT_year());
					}
				}

				if (gcr.getGrade_3() > 0) {
					for (int i = 1; i <= gcr.getGrade_3(); i++) {
						teacherHistoryService.saveTeacherHistory(3, i, gcr.getT_year());
					}
				}

				if (gcr.getGrade_4() > 0) {
					for (int i = 1; i <= gcr.getGrade_4(); i++) {
						teacherHistoryService.saveTeacherHistory(4, i, gcr.getT_year());
					}
				}

				if (gcr.getGrade_5() > 0) {
					for (int i = 1; i <= gcr.getGrade_5(); i++) {
						teacherHistoryService.saveTeacherHistory(5, i, gcr.getT_year());
					}
				}

				if (gcr.getGrade_6() > 0) {
					for (int i = 1; i <= gcr.getGrade_6(); i++) {
						teacherHistoryService.saveTeacherHistory(6, i, gcr.getT_year());
					}
				}

				resultMap.put("res_code", "200");
				resultMap.put("res_msg", gcr.getT_year() + "년도의 반 생성에 성공하였습니다.");
			}
			
		} catch (Exception e) {
			resultMap.put("res_code", "500");
			resultMap.put("res_msg", "서버 오류가 발생하였습니다.");
		}

		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/class-year/{t_year}")
	public Map<String, String> addTeacher(TeacherHistoryDto dto) {
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "직원 반 등록 중 오류가 발생하였습니다.");
		
		System.out.println(dto.getTeach_emp_id());
		
		if(teacherHistoryService.addTeacher(dto) > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "직원 반 배정에 성공하였습니다.");
		}
		
		return resultMap;
	}
	
	
}
