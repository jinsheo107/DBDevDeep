package com.dbdevdeep.notice.domain;

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
public class NoticeAttachmentDto {
	
	private Long att_no;
	private int notice_no;
	private String ori_file_name;
	private String new_file_name;
	
	public NoticeAttachment toNoticeAttachment() {
		return NoticeAttachment.builder()
				.attNo(att_no)
				.oriFileName(ori_file_name)
				.newFileName(new_file_name)
				.build();
	}
	
	public NoticeAttachmentDto toDto(NoticeAttachment na) {
		return NoticeAttachmentDto.builder()
				.att_no(na.getAttNo())
				.ori_file_name(na.getOriFileName())
				.new_file_name(na.getNewFileName())
				.build();
	}
	
}