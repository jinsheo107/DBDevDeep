package com.dbdevdeep.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.notice.domain.Notice;
import com.dbdevdeep.notice.domain.NoticeDto;
import com.dbdevdeep.notice.repository.NoticeRepository;

@Service
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	@Autowired
	public NoticeService(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}
	
	
	public List<NoticeDto> selectNoticeList(NoticeDto noticeDto){
		List<Notice> noticeList = noticeRepository.findAll();
		
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
			noticeDtoList.add(dto);
		}
		
		return noticeDtoList;
	}

}
