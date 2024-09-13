package com.dbdevdeep.chat.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.dbdevdeep.chat.domain.ChatRoom;
import com.dbdevdeep.notice.domain.Notice;

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
public class ChatRoomDto {
	
	private Long room_no;
	
	private String room_name;
	
	private String last_chat;
	@DateTimeFormat(pattern = "yyyy.MM.dd HH:mm")
	private LocalDateTime last_time;
	
	public ChatRoom toEntity() {
		return ChatRoom.builder()
				.roomNo(room_no)
				.roomName(room_name)
				.lastChat(last_chat)
				.lastTime(last_time)
				.build();
	}
	
	public ChatRoomDto toDto(ChatRoom cr) {
		return ChatRoomDto.builder()
				.room_no(cr.getRoomNo())
				.room_name(cr.getRoomName())
				.last_chat(cr.getLastChat())
				.last_time(cr.getLastTime())
				.build();
	}
}
