package com.dbdevdeep.student.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StudentDto {
	private Long student_no;
	private String student_name;
	private String student_birth;
	private String student_gender;
	private String student_address_no;
	private String student_address;
	private String student_detail_address;
	private String student_phone;
	private String student_ori_profile;
	private String student_new_profile;
	private String student_status;
	
	public Student toEntity() {
		return Student.builder()
				.studentNo(student_no)
				.studentName(student_name)
				.studentBirth(student_birth)
				.studentGender(student_gender)
				.studentAddressNo(student_address_no)
				.studentAddress(student_address)
				.studentDetailAddress(student_detail_address)
				.studentPhone(student_phone)
				.studentOriProfile(student_ori_profile)
				.studentNewProfile(student_new_profile)
				.studentStatus(student_status)
				.build();
	}
	
	public StudentDto toDto(Student student) {
		return StudentDto.builder()
				.student_no(student.getStudentNo())
				.student_name(student.getStudentName())
				.student_birth(student.getStudentBirth())
				.student_gender(student.getStudentGender())
				.student_address_no(student.getStudentAddressNo())
				.student_address(student.getStudentAddress())
				.student_detail_address(student.getStudentDetailAddress())
				.student_phone(student.getStudentPhone())
				.student_ori_profile(student.getStudentOriProfile())
				.student_new_profile(student.getStudentNewProfile())
				.student_status(student.getStudentStatus())
				.build();
	}
	
	
}
