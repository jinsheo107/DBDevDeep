package com.dbdevdeep.notice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private List<NoticeCommentDto> childComments;
	
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
		
		/// 자식 댓글 리스트를 초기화
	    List<NoticeCommentDto> childCommentDtos = new ArrayList<>();

	    // 자식 댓글을 NoticeCommentDto로 변환하여 리스트에 추가
	    for (NoticeComment child : ncmt.getChildComments()) {
	        NoticeCommentDto childDto = toDto(child); // 재귀적으로 자식 댓글도 변환
	        childCommentDtos.add(childDto); // 리스트에 추가
	    }
		
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
	        .childComments(childCommentDtos) // 자식 댓글 DTO 리스트를 추가
	        .build();
	}

}
