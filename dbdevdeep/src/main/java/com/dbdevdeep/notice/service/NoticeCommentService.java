package com.dbdevdeep.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<NoticeCommentDto> selectNoticeCommentList(Long notice_no){
		List<NoticeComment> cmtList = noticeCommentRepository.findByNoticeNo(notice_no);
		List<NoticeCommentDto> cmtDtoList = new ArrayList<NoticeCommentDto>();
		
		for(NoticeComment nc : cmtList) {
			NoticeCommentDto ncDto = new NoticeCommentDto().toDto(nc);
			cmtDtoList.add(ncDto);
		}
		return cmtDtoList;
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
}
