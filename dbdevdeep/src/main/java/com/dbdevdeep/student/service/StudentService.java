package com.dbdevdeep.student.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.TeacherHistory;
import com.dbdevdeep.employee.repository.TeacherHistoryRepository;
import com.dbdevdeep.student.domain.Parent;
import com.dbdevdeep.student.domain.ParentDto;
import com.dbdevdeep.student.domain.Student;
import com.dbdevdeep.student.domain.StudentClass;
import com.dbdevdeep.student.domain.StudentClassDto;
import com.dbdevdeep.student.domain.StudentDto;
import com.dbdevdeep.student.repository.ParentRepository;
import com.dbdevdeep.student.repository.StudentClassRepository;
import com.dbdevdeep.student.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
	
	// 의존성 주입
	private final StudentRepository studentRepository;
	private final TeacherHistoryRepository teacherHistoryRepository;
	private final StudentClassRepository studentClassRepository;
	private final ParentRepository parentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository, TeacherHistoryRepository teacherHistoryRepository, StudentClassRepository studentClassRepository, ParentRepository parentRepository) {
		this.studentRepository = studentRepository;
		this.teacherHistoryRepository = teacherHistoryRepository;
		this.studentClassRepository = studentClassRepository;
		this.parentRepository = parentRepository;
	}
	
	// 입력 form에서 받아온 dto data를 Student로 바꿔서 저장하는 절차
	public Student createStudent(StudentDto dto) {
		Student student = Student.builder()
				.studentName(dto.getStudent_name())
				.studentBirth(dto.getStudent_birth())
				.studentGender(dto.getStudent_gender())
				.studentPostCode(dto.getStudent_post_code())
				.studentAddr(dto.getStudent_addr())
				.studentDetailAddr(dto.getStudent_detail_addr())
				.studentPhone(dto.getStudent_phone())
				.studentOriPic(dto.getStudent_ori_pic())
				.studentNewPic(dto.getStudent_new_pic())
				.studentStatus(dto.getStudent_status())
				.build();
		
		return studentRepository.save(student);
				
	}
	
	// 학생리스트를 옮겨주기 위해 dto로 변환하여 담아주는 절차
	public List<StudentClassDto> selectStudentList(StudentClassDto studentClassDto){
		List<StudentClass> studentClassList = studentClassRepository.findRecentYearAll();
		List<StudentClassDto> studentClassDtoList = new ArrayList<>();
		for(StudentClass sc : studentClassList) {
			StudentClassDto dto = new StudentClassDto().toDto(sc);
			studentClassDtoList.add(dto);
		}
		return studentClassDtoList;
	} 
	
	// 학생번호를 통해 선택한 학생의 정보를 dto로 변환하여 담아주는 절차
	public StudentDto selectStudentOne(Long student_no) {
		Student student = studentRepository.findBystudentNo(student_no);
		StudentDto dto = StudentDto.builder()
				.student_no(student.getStudentNo())
				.student_name(student.getStudentName())
				.student_birth(student.getStudentBirth())
				.student_gender(student.getStudentGender())
				.student_post_code(student.getStudentPostCode())
				.student_addr(student.getStudentAddr())
				.student_detail_addr(student.getStudentDetailAddr())
				.student_phone(student.getStudentPhone())
				.student_ori_pic(student.getStudentOriPic())
				.student_new_pic(student.getStudentNewPic())
				.student_status(student.getStudentStatus())
				.build();
			return dto;
	}
	
	// 수정 정보를 담아 entity로 변환 후에 DB에 저장하는 과정
	@Transactional
	public Student updateStudentInfo(StudentDto dto) {
		StudentDto temp = selectStudentOne(dto.getStudent_no());
		temp.setStudent_name(dto.getStudent_name());
		temp.setStudent_birth(dto.getStudent_birth());
		temp.setStudent_gender(dto.getStudent_gender());
		temp.setStudent_post_code(dto.getStudent_post_code());
		temp.setStudent_addr(dto.getStudent_addr());
		temp.setStudent_detail_addr(dto.getStudent_detail_addr());
		temp.setStudent_phone(dto.getStudent_phone());
		temp.setStudent_ori_pic(dto.getStudent_ori_pic());
		temp.setStudent_new_pic(dto.getStudent_new_pic());
		temp.setStudent_status(dto.getStudent_status());
		if(dto.getStudent_ori_pic() != null
				&& "".equals(dto.getStudent_ori_pic()) == false) {
			temp.setStudent_ori_pic(dto.getStudent_ori_pic());
			temp.setStudent_new_pic(dto.getStudent_new_pic());
		}
		Student std = temp.toEntity();
		Student result = studentRepository.save(std);
		return result;
	}
	
	// 학생정보 삭제
	public int deleteStudent(Long student_no) {
		int result = 0;
		try {
			studentRepository.deleteById(student_no);
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 반배정
	public StudentClass assignClass(StudentClassDto dto) {
			Student sdt = studentRepository.findBystudentNo(dto.getStudent_no());
			TeacherHistory th = teacherHistoryRepository.findByteacherNo(dto.getTeacher_no());
			StudentClass sc= StudentClass.builder()
					.student(sdt)
					.teacherHistory(th)
					.studentId(dto.getStudent_id())
					.build();
			
			return studentClassRepository.save(sc);
	}
	
	// 반배정 이력 조회
	public List<StudentClassDto> selectStudentClassList(Long student_no){
		List<StudentClass> studentClassList = studentClassRepository.findByStudent_StudentNo(student_no);
		List<StudentClassDto> studentClassDtoList = new ArrayList<StudentClassDto>();
		for(StudentClass sc : studentClassList) {
			StudentClassDto dto = new StudentClassDto().toDto(sc);
			studentClassDtoList.add(dto);
		}
		return studentClassDtoList;
	}
	
	// 반배정 이력 삭제
	public int deleteStudentClass(Long class_no) {
		int result = 0;
		try {
			studentClassRepository.deleteById(class_no);
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 학생의 부모 리스트 조회
	public List<ParentDto> selectStudentParentList(Long student_no) {
		List<Parent> parentList = parentRepository.findByStudent_StudentNo(student_no);
		List<ParentDto> parentDtoList = new ArrayList<ParentDto>();
		for(Parent p : parentList) {
			ParentDto dto = new ParentDto().toDto(p);
			parentDtoList.add(dto);
		}
		return parentDtoList;
	}
	
	/*
	 * // 학부모 등록 public Parent createParent(ParentDto dto) { Parent parent =
	 * Parent.builder() .parentNo(dto.getParent_no())
	 * .studentNo(dto.getStudent_no()) .parentName(dto.getParent_name())
	 * .parentPhone(dto.getParent_phone()) .parentBirth(dto.getParent_birth())
	 * .build();
	 * 
	 * return parentRepository.save(parent); }
	 */
	
}
