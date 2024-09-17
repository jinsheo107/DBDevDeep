package com.dbdevdeep.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.notice.domain.Notice;
import com.dbdevdeep.notice.domain.NoticeComment;
import com.dbdevdeep.notice.dto.NoticeCommentDto;
import com.dbdevdeep.notice.repository.NoticeCommentRepository;
import com.dbdevdeep.notice.repository.NoticeRepository;

@Service
public class NoticeCommentService {
	
	private final NoticeCommentRepository noticeCommentRepository;
	private final NoticeRepository noticeRepository;
	private final EmployeeRepository employeeRepository;
	@Autowired
	public NoticeCommentService(NoticeCommentRepository noticeCommentRepository, 
			NoticeRepository noticeRepository, 
			EmployeeRepository employeeRepository) {
		this.noticeCommentRepository = noticeCommentRepository;
		this.noticeRepository = noticeRepository;
		this.employeeRepository = employeeRepository;
	}
	
	// 댓글 목록 조회
	public List<NoticeCommentDto> selectNoticeCommentList(Long noticeNo){
		
		// 댓글 리스트 조회 (부모 댓글만)
		List<NoticeComment> parentComments = 
				noticeCommentRepository.findByNoticeNoticeNoAndParentCommentIsNull(noticeNo);
		
        // 부모 댓글에 대한 자식 댓글을 조회하고 DTO로 변환
        List<NoticeCommentDto> commentDtos = new ArrayList<>();
        for (NoticeComment parentComment : parentComments) {
            NoticeCommentDto parentDto = new NoticeCommentDto().toDto(parentComment);
            
            // 자식 댓글 조회
            List<NoticeComment> childComments = 
            		noticeCommentRepository.findByParentCommentCmtNo(parentComment.getCmtNo());
            
            List<NoticeCommentDto> childDtos = new ArrayList<>();
            for (NoticeComment childComment : childComments) {
                childDtos.add(new NoticeCommentDto().toDto(childComment));
                
            }

            // 부모 DTO에 자식 댓글 리스트를 설정
            parentDto.setChildComments(childDtos);
            commentDtos.add(parentDto);
        }

        return commentDtos;
    
	}
	
	// 댓글 작성
	public int createNoticeComment(NoticeCommentDto dto) {
		int result = -1;
		
		try {
			Notice n = noticeRepository.findBynoticeNo(dto.getNotice_no());
			Employee e = employeeRepository.findByempId(dto.getWriter_id());
			
			NoticeComment nc = dto.toEntity(n, e, null);
			
			noticeCommentRepository.save(nc);
			result = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 댓글 삭제
	@Transactional
	public int deleteComment(Long cmtNo) {
		int result =-1;
		
		NoticeComment nc = noticeCommentRepository.findBycmtNo(cmtNo);
		
		if(nc.getIsDelete()==0) {
			int isDelete = 1;
			result = noticeCommentRepository.deleteCmtId(cmtNo,isDelete);
		}
		
		return result;
	}
	
	// 댓글 수정
}
