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
	private boolean cmt_agree;
	private LocalDateTime reg_date;
	private LocalDateTime mod_date;
	private boolean att_y_n;
	private boolean read_check = false;

	public Notice toEntity() {
		return Notice.builder()
				.noticeNo(notice_no)
				.noticeTitle(notice_title)
				.noticeContent(notice_content)
				.isImportant(is_important)
				.cmtAgree(cmt_agree)
				.regDate(reg_date)
				.modDate(mod_date)
				.attYN(att_y_n)
				.build();
	}
	
	public NoticeDto toDto(Notice n) {
		return NoticeDto.builder()
				.notice_no(n.getNoticeNo())
				.notice_title(n.getNoticeTitle())
				.notice_content(n.getNoticeContent())
				.is_important(n.isImportant())
				.cmt_agree(n.isCmtAgree())
				.reg_date(n.getRegDate())
				.mod_date(n.getModDate())
				.att_y_n(n.isAttYN())
				.build();
				
	}
	
}
