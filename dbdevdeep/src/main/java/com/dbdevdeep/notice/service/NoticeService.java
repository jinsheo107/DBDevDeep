package com.dbdevdeep.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.dbdevdeep.notice.domain.Notice;
import com.dbdevdeep.notice.domain.NoticeReadCheck;
import com.dbdevdeep.notice.dto.NoticeDto;
import com.dbdevdeep.notice.repository.NoticeReadCheckRepository;
import com.dbdevdeep.notice.repository.NoticeRepository;

@Service
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	private final NoticeReadCheckRepository noticeReadCheckRepository;
	@Autowired
	public NoticeService(NoticeRepository noticeRepository, NoticeReadCheckRepository noticeReadCheckRepository) {
		this.noticeRepository = noticeRepository;
		this.noticeReadCheckRepository = noticeReadCheckRepository;
	}
	
	
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
					.cmt_agree(n.isCmtAgree())
					.reg_date(n.getRegDate())
					.mod_date(n.getModDate())
					.att_y_n(n.isAttYN())
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

}
