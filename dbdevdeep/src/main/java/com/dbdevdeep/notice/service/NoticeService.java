package com.dbdevdeep.notice.service;

import java.time.LocalDateTime;
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
import com.dbdevdeep.notice.domain.NoticeCategory;
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
					.is_important(n.getIsImportant())
					.is_cmt(n.getIsCmt())
					.reg_time(n.getRegTime())
					.mod_time(n.getModTime())
					.is_att(n.getIsAtt())
					.build();
			
			for(NoticeReadCheck nrc : nrcList) {
				if(n.getNoticeNo()==nrc.getNotice().getNoticeNo()) {
					dto.setRead_check(1);
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
				.is_important(n.getIsImportant())
				.is_cmt(n.getIsCmt())
				.reg_time(n.getRegTime())
				.mod_time(n.getModTime())
				.is_att(n.getIsAtt())
				.build();
		
		return dto;
	}
	
	// 읽음 확인
	public int readCheck(String read_id, Long notice_no) {
		int result=-1;
		NoticeReadCheck nrc = noticeReadCheckRepository.findByEmployeeIdAndNoticeId(read_id, notice_no);
		if(nrc==null) {
			Employee e = employeeRepository.findByempId(read_id);
			Notice n = noticeRepository.findBynoticeNo(notice_no);
			
			NoticeReadCheck newNrc = NoticeReadCheck.builder()
					.employee(e)
					.notice(n)
					.build();
			
			noticeReadCheckRepository.save(newNrc);
			result=1;
		}
		return result;
	}
	
	// 공지사항 게시글 작성
	public int createNotice(NoticeDto dto) {
		int result = -1;
		
		try {
			Employee e = employeeRepository.findByempId(dto.getWriter_id());
			NoticeCategory nc = noticeCategoryRepository.findBycategoryNo(dto.getCategory_no());
			
			Notice n = Notice.builder()
					.noticeNo(dto.getNotice_no())
					.employee(e)
					.noticeCategory(nc)
					.noticeTitle(dto.getNotice_title())
					.noticeContent(dto.getNotice_content())
					.isImportant(dto.getIs_important())
					.isCmt(dto.getIs_cmt())
					.isAtt(dto.getIs_att())
					.build();
			
			noticeRepository.save(n);
			result = 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	// 공지사항 게시글 수정
	public int updateNotice(NoticeDto dto) {
		int result = -1;
		
		try {
			Employee e = employeeRepository.findByempId(dto.getWriter_id());
			NoticeCategory nc = noticeCategoryRepository.findBycategoryNo(dto.getCategory_no());
			
			Notice n = Notice.builder()
					.noticeNo(dto.getNotice_no())
					.employee(e)
					.noticeCategory(nc)
					.noticeTitle(dto.getNotice_title())
					.noticeContent(dto.getNotice_content())
					.isImportant(dto.getIs_important())
					.isCmt(dto.getIs_cmt())
					.isAtt(dto.getIs_att())
					.build();
			
			noticeRepository.save(n);
			result = 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	// 공지사항 게시글 삭제
	public int deleteNotice(Long notice_no) {
		int result = -1;
		try {
			noticeRepository.deleteById(notice_no);
			result = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
