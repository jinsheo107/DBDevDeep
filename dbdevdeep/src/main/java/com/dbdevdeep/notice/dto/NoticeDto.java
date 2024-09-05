package com.dbdevdeep.notice.dto;

import java.time.LocalDateTime;

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
public class NoticeDto {
	
	private Long notice_no;
	private String writer_id;
	private String writer_name;
	private Long category_no;
	private String category_name;
	private String notice_title;
	private String notice_content;
	private boolean is_important;
	private boolean is_cmt;
	private LocalDateTime reg_time;
	private LocalDateTime mod_time;
	private boolean is_att;
	private boolean read_check = false;

	public Notice toEntity() {
		return Notice.builder()
				.noticeNo(notice_no)
				.noticeTitle(notice_title)
				.noticeContent(notice_content)
				.isImportant(is_important)
				.isCmt(is_cmt)
				.regTime(reg_time)
				.modTime(mod_time)
				.isAtt(is_att)
				.build();
	}
	
	public NoticeDto toDto(Notice n) {
		return NoticeDto.builder()
				.notice_no(n.getNoticeNo())
				.notice_title(n.getNoticeTitle())
				.notice_content(n.getNoticeContent())
				.is_important(n.isImportant())
				.is_cmt(n.isCmt())
				.reg_time(n.getRegTime())
				.mod_time(n.getModTime())
				.is_att(n.isAtt())
				.build();
				
	}
	
}
