package com.dbdevdeep.chat.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.dbdevdeep.chat.domain.ChatMsg;
import com.dbdevdeep.chat.domain.ChatRoom;
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
public class ChatMsgDto {
	private Long msg_no;
	private Long room_no;
	private String writer_id;
	private String chat_content;
	private int is_important;
	@DateTimeFormat(pattern = "yyyy.MM.dd HH:mm")
	private LocalDateTime reg_time;
	private int is_edited;
	private LocalDateTime mod_time;
	private String ori_pic_name;
	private String new_pic_name;
	
	public ChatMsg toEntity(ChatRoom cr, Employee e) {
		return ChatMsg.builder()
				.msgNo(msg_no)
				.chatRoom(cr)
				.employee(e)
				.chatContent(chat_content)
				.isImportant(is_important)
				.regTime(reg_time)
				.isEdited(is_edited)
				.modTime(mod_time)
				.oriPicName(ori_pic_name)
				.newPicName(new_pic_name)
				.build();
	}
	
	public ChatMsgDto toDto(ChatMsg cm) {
		return ChatMsgDto.builder()
				.msg_no(cm.getMsgNo())
				.room_no(cm.getChatRoom().getRoomNo())
				.writer_id(cm.getEmployee().getEmpId())
				.chat_content(cm.getChatContent())
				.is_important(cm.getIsImportant())
				.reg_time(cm.getRegTime())
				.is_edited(cm.getIsEdited())
				.mod_time(cm.getModTime())
				.ori_pic_name(cm.getOriPicName())
				.new_pic_name(cm.getNewPicName())
				.build();		
	}
	
}
