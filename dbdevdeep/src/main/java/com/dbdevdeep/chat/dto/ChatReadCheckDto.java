package com.dbdevdeep.chat.dto;

import java.time.LocalDateTime;

import com.dbdevdeep.chat.domain.ChatMsg;
import com.dbdevdeep.chat.domain.ChatReadCheck;
import com.dbdevdeep.employee.domain.Employee;

import groovy.transform.ToString;
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
	private Long msg_no;
	private String read_id;
	private LocalDateTime read_time;
	
	public ChatReadCheck toEntity(ChatMsg cm, Employee e) {
		return ChatReadCheck.builder()
				.checkNo(check_no)
				.chatMsg(cm)
				.employee(e)
				.readTime(read_time)
				.build();
	}
	
	public ChatReadCheckDto toDto(ChatReadCheck crc) {
		return ChatReadCheckDto.builder()
				.check_no(crc.getCheckNo())
				.msg_no(crc.getChatMsg().getMsgNo())
				.read_id(crc.getEmployee().getEmpId())
				.read_time(crc.getReadTime())
				.build();
	}
	
}
