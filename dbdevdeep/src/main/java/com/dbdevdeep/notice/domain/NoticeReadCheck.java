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
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="notice_read_check", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"notice_no", "read_id"})})
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class NoticeReadCheck {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="check_no")
	private Long checkNo;
	
	@ManyToOne
	@JoinColumn(name="notice_no")
	private Notice notice;
	
	@ManyToOne
	@JoinColumn(name="read_id")
	private Employee employee;
	
	@Column(name="read_date")
	private LocalDateTime read_date;
	
}
