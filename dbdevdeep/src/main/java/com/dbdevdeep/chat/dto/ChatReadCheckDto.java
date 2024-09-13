package com.dbdevdeep.chat.dto;

import java.time.LocalDateTime;

import com.dbdevdeep.chat.domain.ChatMsg;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.notice.dto.NoticeDto;

import groovy.transform.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ChatReadCheckDto {
	
	private Long check_no;

	private ChatMsg msg_no;
	
	private Employee employee;
	
	@Column(name="read_time")
	private LocalDateTime readTime;
}
