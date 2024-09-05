package com.dbdevdeep.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.student.domain.Student;
import com.dbdevdeep.student.domain.StudentDto;
import com.dbdevdeep.student.repository.StudentRepository;

@Service
public class StudentService {
	
	private final StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
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
}
