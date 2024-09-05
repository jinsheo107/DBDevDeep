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
	private String student_post_code;
	private String student_addr;
	private String student_detail_addr;
	private String student_phone;
	private String student_ori_pic;
	private String student_new_pic;
	private String student_status;
	
	public Student toEntity() {
		return Student.builder()
				.studentNo(student_no)
				.studentName(student_name)
				.studentBirth(student_birth)
				.studentGender(student_gender)
				.studentPostCode(student_post_code)
				.studentAddr(student_addr)
				.studentDetailAddr(student_detail_addr)
				.studentPhone(student_phone)
				.studentOriPic(student_ori_pic)
				.studentNewPic(student_new_pic)
				.studentStatus(student_status)
				.build();
	}
	
	public StudentDto toDto(Student student) {
		return StudentDto.builder()
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
	}
	
	
}
