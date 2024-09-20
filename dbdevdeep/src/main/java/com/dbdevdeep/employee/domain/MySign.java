package com.dbdevdeep.employee.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="signature")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class MySign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sign_no")
	private Long signNo;
	
	@ManyToOne
	@JoinColumn(name="emp_id")
	private Employee employee;
	
	@Column(name="sign_type")
	private String signType;
	
	@Column(name="ori_pic_name")
	private String oriPicName;
	
	@Column(name="new_pic_name")
	private String newPicName;
	
	@Column(name="sign_title")
	private String signTitle;
	
	@Column(name="reg_time")
	private LocalDateTime regTime;
	
	@Column(name="mod_time")
	private LocalDateTime modTime;
	
	@Column(name="rep_yn")
	private String repYn;
	
}
