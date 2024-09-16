package com.dbdevdeep.notice.dto;

import java.time.LocalDateTime;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.notice.domain.Notice;
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
	private Long notice_no;
	private String writer_id;
	private String writer_name;
	private Long parent_cmt_no;
    private String cmt_content;
    private LocalDateTime reg_time;
	private LocalDateTime mod_time;
	private int is_delete;
	
	public NoticeComment toEntity(Notice n,Employee e,NoticeComment nc) {
		return NoticeComment.builder()
				.cmtNo(cmt_no)
				.notice(n)
				.employee(e)
				.parentComment(nc)
				.cmtContent(cmt_content)
				.regTime(reg_time)
				.modTime(mod_time)
				.isDelete(is_delete)
				.build();		
	}
	
	public NoticeCommentDto toDto(NoticeComment ncmt) {
	    return NoticeCommentDto.builder()
	        .cmt_no(ncmt.getCmtNo())
	        .notice_no(ncmt.getNotice().getNoticeNo())
	        .writer_id(ncmt.getEmployee().getEmpId())
	        .writer_name(ncmt.getEmployee().getEmpName())
	        .cmt_content(ncmt.getCmtContent())
	        .reg_time(ncmt.getRegTime())
	        .mod_time(ncmt.getModTime())
	        .is_delete(ncmt.getIsDelete())
	        // 부모 댓글이 null인지 확인하고, null이 아니면 parent comment의 cmt_no를 설정
	        .parent_cmt_no(ncmt.getParentComment() != null ? ncmt.getParentComment().getCmtNo() : null)
	        .build();
	}

}
