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
				.studentAddressNo(dto.getStudent_address_no())
				.studentAddress(dto.getStudent_address())
				.studentDetailAddress(dto.getStudent_detail_address())
				.studentPhone(dto.getStudent_phone())
				.studentOriProfile(dto.getStudent_ori_profile())
				.studentNewProfile(dto.getStudent_new_profile())
				.studentStatus(dto.getStudent_status())
				.build();
		
		return studentRepository.save(student);
				
	}
	
	// 학생리스트를 옮겨주기 위해 dto로 변환하여 담아주는 절차
	public List<StudentDto> selectStudentList(StudentDto studentDto){
		List<Student> studentList = studentRepository.findAll();
		
		List<StudentDto> studentDtoList = new ArrayList<StudentDto>();
		for(Student s : studentList) {
			StudentDto dto = new StudentDto().toDto(s);
		}
		return studentDtoList;
	} 
	
	
	
}
