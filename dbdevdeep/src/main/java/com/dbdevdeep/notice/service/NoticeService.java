package com.dbdevdeep.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.notice.domain.Notice;
import com.dbdevdeep.notice.domain.NoticeReadCheck;
import com.dbdevdeep.notice.dto.NoticeDto;
import com.dbdevdeep.notice.repository.NoticeCategoryRepository;
import com.dbdevdeep.notice.repository.NoticeReadCheckRepository;
import com.dbdevdeep.notice.repository.NoticeRepository;

@Service
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	private final NoticeReadCheckRepository noticeReadCheckRepository;
	private final EmployeeRepository employeeRepository;
	private final NoticeCategoryRepository noticeCategoryRepository;
	@Autowired
	public NoticeService(NoticeRepository noticeRepository, 
			NoticeReadCheckRepository noticeReadCheckRepository, 
			EmployeeRepository employeeRepository,
			NoticeCategoryRepository noticeCategoryRepository) {
		this.noticeRepository = noticeRepository;
		this.noticeReadCheckRepository = noticeReadCheckRepository;
		this.employeeRepository = employeeRepository;
		this.noticeCategoryRepository = noticeCategoryRepository;
	}
	
	// 공지사항 목록 조회
	public List<NoticeDto> selectNoticeList(NoticeDto noticeDto){
		// 게시글 리스트 불러오기
		List<Notice> noticeList = noticeRepository.findAll();
		// 게시글 읽음확인 리스트 불러오기
		// 1. 로그인한 사용자의 정보 불러오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal(); // security 입장의 name(id), pw
		// 2. 로그인한 사용자의 읽음확인 리스트 불러오기
		List<NoticeReadCheck> nrcList = noticeReadCheckRepository.findByEmployee_EmpId(user.getUsername());
		
		// Entity 데이터를 Dto로 옮기기
		List<NoticeDto> noticeDtoList = new ArrayList<NoticeDto>();
		for(Notice n : noticeList) {
			NoticeDto dto = NoticeDto.builder()
					.notice_no(n.getNoticeNo())
					.writer_id(n.getEmployee().getEmpId())
					.writer_name(n.getEmployee().getEmpName())
					.category_no(n.getNoticeCategory().getCategoryNo())
					.category_name(n.getNoticeCategory().getCategoryName())
					.notice_title(n.getNoticeTitle())
					.notice_content(n.getNoticeContent())
					.is_important(n.isImportant())
					.is_cmt(n.isCmt())
					.reg_time(n.getRegTime())
					.mod_time(n.getModTime())
					.is_att(n.isAtt())
					.build();
			
			for(NoticeReadCheck nrc : nrcList) {
				if(n.getNoticeNo()==nrc.getNotice().getNoticeNo()) {
					dto.setRead_check(true);
				}
			}
			noticeDtoList.add(dto);
		}
		
		return noticeDtoList;
	}
	
	// 공지사항 게시글 상세조회
	public NoticeDto selectNoticeOne(Long notice_no) {
		
		Notice n = noticeRepository.findBynoticeNo(notice_no);
		
		NoticeDto dto = NoticeDto.builder()
				.notice_no(n.getNoticeNo())
				.writer_id(n.getEmployee().getEmpId())
				.writer_name(n.getEmployee().getEmpName())
				.category_no(n.getNoticeCategory().getCategoryNo())
				.category_name(n.getNoticeCategory().getCategoryName())
				.notice_title(n.getNoticeTitle())
				.notice_content(n.getNoticeContent())
				.is_important(n.isImportant())
				.is_cmt(n.isCmt())
				.reg_time(n.getRegTime())
				.mod_time(n.getModTime())
				.is_att(n.isAtt())
				.build();
		
		return dto;
	}
	
	// 공지사항 게시글 작성
	public int createNotice(NoticeDto dto) {
		int result = -1;
		
		try {
			Employee e = employeeRepository.findByempId(dto.getWriter_id());
			
			
//			Notice n = Notice.builder()
//					.noticeNo(dto.getNotice_no())
//					.employee(e)
////					.noticeCategory(nc)
			
			
//			noticeRepository.save(n);
			result = 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
