package com.dbdevdeep.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.student.domain.Student;
import com.dbdevdeep.student.domain.StudentDto;
import com.dbdevdeep.student.repository.StudentRepository;

@Service
public class StudentService {
	
	// 의존성 주입
	private final StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
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
	public List<StudentDto> selectStudentList(StudentDto studentDto){
		List<Student> studentList = studentRepository.findAll();
		System.out.println(studentList);
		List<StudentDto> studentDtoList = new ArrayList<StudentDto>();
		for(Student s : studentList) {
			StudentDto dto = new StudentDto().toDto(s);
			studentDtoList.add(dto);
		}
		return studentDtoList;
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
	
	
	
}
