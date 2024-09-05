package com.dbdevdeep.notice.domain;

import java.time.LocalDateTime;

import com.dbdevdeep.employee.domain.Employee;


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
import lombok.Setter;

@Entity
@Table(name="notice")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class Notice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="notice_no")
	private Long noticeNo;
	
	@ManyToOne
	@JoinColumn(name="writer_id")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="category_no")
	private NoticeCategory noticeCategory;
	
	@Column(name="notice_title")
	private String noticeTitle;
	
	@Column(name="notice_content")
	private String noticeContent;
	
	@Column(name="is_important")
	private boolean isImportant;
	
	@Column(name="is_cmt")
	private boolean isCmt;
	
	@Column(name="reg_time")
	private LocalDateTime regTime;
	
	@Column(name="mod_time")
	private LocalDateTime modTime;
	
	@Column(name="is_att")
	private boolean isAtt;
	
}
