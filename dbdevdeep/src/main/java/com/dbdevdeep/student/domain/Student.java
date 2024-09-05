package com.dbdevdeep.student.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="student_info")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_no")
	private Long studentNo;
	
	@Column(name="student_name")
	private String studentName;
	
	@Column(name="student_birth")
	private String studentBirth;
	
	@Column(name="student_gender")
	private String studentGender;
	
	@Column(name="student_post_code")
	private String studentPostCode;
	
	@Column(name="student_addr")
	private String studentAddr;
	
	@Column(name="student_detail_addr")
	private String studentDetailAddr;
	
	@Column(name="student_phone")
	private String studentPhone;
	
	@Column(name="student_ori_pic")
	private String studentOriPic;
	
	@Column(name="student_new_pic")
	private String studentNewPic;
	
	@Column(name="student_status")
	private String studentStatus;
	
}