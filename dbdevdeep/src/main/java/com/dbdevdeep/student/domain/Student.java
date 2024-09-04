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
	
	@Column(name="student_address_no")
	private String studentAddressNo;
	
	@Column(name="student_address")
	private String studentAddress;
	
	@Column(name="student_detail_address")
	private String studentDetailAddress;
	
	@Column(name="student_phone")
	private String studentPhone;
	
	@Column(name="student_ori_profile")
	private String studentOriProfile;
	
	@Column(name="student_new_profile")
	private String studentNewProfile;
	
	@Column(name="student_status")
	private String studentStatus;
	
}