package com.dbdevdeep.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		
		
		if(teacherHistoryService.addTeacher(dto) > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "직원 반 배정에 성공하였습니다.");
		}
		
		return resultMap;
	}
	
	@ResponseBody
	@PutMapping("/class-year/{t_year}")
	public Map<String, String> editTeacher(@RequestBody GradeClassRequest gcr, @PathVariable("t_year") String t_year) {
		
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "반 생성 중 오류가 발생하였습니다.");
		
		int total = 0;
		int real = 0;
				
		try {
			if (teacherHistoryService.tYearCheck(gcr.getT_year()) == 1) {
				
				
				int[] gradeArray = new int[6];
				
				gradeArray[0] = gcr.getGrade_1();
				gradeArray[1] = gcr.getGrade_2();
				gradeArray[2] = gcr.getGrade_3();
				gradeArray[3] = gcr.getGrade_4();
				gradeArray[4] = gcr.getGrade_5();
				gradeArray[5] = gcr.getGrade_6();
				
				for(int i = 0; i < gradeArray.length; i++) {
					int count = teacherHistoryService.tYearGradeCount(gcr.getT_year(), i + 1);
					
					System.out.println(real + ", " + total);
					
					if(count < gradeArray[i]) {
						for(int j = 0; j < gradeArray[i] - count; j++) {
							if(teacherHistoryService.saveTeacherHistory(i + 1, count + j + 1, gcr.getT_year()) > 0) {
								real++;
							}
							total++;
						}
					} else if (count > gradeArray[i]){
						for(int j = 0; j < count - gradeArray[i]; j++) {
							if(teacherHistoryService.deleteByGradeClassTyear(i + 1, count - j, gcr.getT_year()) > 0) {
								real++;
							}
							total++;
						}
					} 
				}
			} 
			
			if(total == real) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", gcr.getT_year() + "년도의 반 수정에 성공하였습니다.");
			}
			
		} catch (Exception e) {
			resultMap.put("res_code", "500");
			resultMap.put("res_msg", "서버 오류가 발생하였습니다.");
		}

		return resultMap;
	}
	
}
