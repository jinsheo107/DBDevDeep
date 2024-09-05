package com.dbdevdeep.notice.dto;

import java.time.LocalDateTime;

import com.dbdevdeep.notice.domain.NoticeComment;

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
    private LocalDateTime reg_time;
	private LocalDateTime mod_time;
	
	public NoticeComment toEntity() {
		return NoticeComment.builder()
				.cmtNo(cmt_no)
				.cmtContent(cmt_content)
				.regTime(reg_time)
				.modTime(mod_time)
				.build();		
	}
	
	public NoticeCommentDto toDto(NoticeComment ncmt) {
		return NoticeCommentDto.builder()
				.cmt_no(ncmt.getCmtNo())
				.cmt_content(ncmt.getCmtContent())
				.reg_time(ncmt.getRegTime())
				.mod_time(ncmt.getModTime())
				.build();
	}

}
