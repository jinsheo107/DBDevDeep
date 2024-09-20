package com.dbdevdeep.notice.dto;

import java.time.LocalDateTime;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.notice.domain.Notice;
import com.dbdevdeep.notice.domain.NoticeReadCheck;

import groovy.transform.ToString;
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

@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@Builder
public class NoticeReadCheckDto {
	

	private Long check_no;
	private Long notice_no;
	private String read_id;
	private LocalDateTime read_time;
	
	public NoticeReadCheck toEntity(Employee e, Notice n) {
		return NoticeReadCheck.builder()
				.checkNo(check_no)
				.notice(n)
				.employee(e)
				.readTime(read_time)
				.build();
	}
	
	public NoticeReadCheckDto toDto(NoticeReadCheck nrc) {
		return NoticeReadCheckDto.builder()
				.check_no(nrc.getCheckNo())
				.notice_no(nrc.getNotice().getNoticeNo())
				.read_id(nrc.getEmployee().getEmpId())
				.read_time(nrc.getReadTime())
				.build();
	}
	
}
