package com.dbdevdeep.notice.domain;

import java.time.LocalDateTime;

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
public class NoticeCommentDto {

	private Long cmt_no;
	private int notice_no;
	private String writer_id;
	private int parent_cmt_no;
    private String cmt_content;
    private LocalDateTime reg_date;
	private LocalDateTime mod_date;
	
	public NoticeComment toEntity() {
		return NoticeComment.builder()
				.cmtNo(cmt_no)
				.cmtContent(cmt_content)
				.regDate(reg_date)
				.modDate(mod_date)
				.build();		
	}
	
	public NoticeCommentDto toDto(NoticeComment ncmt) {
		return NoticeCommentDto.builder()
				.cmt_no(ncmt.getCmtNo())
				.cmt_content(ncmt.getCmtContent())
				.reg_date(ncmt.getRegDate())
				.mod_date(ncmt.getModDate())
				.build();
	}

}
