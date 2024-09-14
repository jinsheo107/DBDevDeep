package com.dbdevdeep.approve.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.approve.domain.ApproveLine;
import com.dbdevdeep.approve.domain.ApproveLineDto;
import com.dbdevdeep.approve.repository.ApproveLineRepository;

@Service
public class ApproveLineService {

	private final ApproveLineRepository approveLineRepository;
	
	@Autowired
	public ApproveLineService(ApproveLineRepository approveLineRepository) {
		this.approveLineRepository = approveLineRepository;
	}
	
	// 반려 정보 가져오는 메서드
    public ApproveLineDto approBackReason(Long approNo) {
        // 반려 정보 조회
        ApproveLine backReason = approveLineRepository.backReasonByApproveNo(approNo);

        // null 체크 후 null일 경우 예외를 던지거나 기본값 반환
        if (backReason == null) {
            throw new NoSuchElementException("반려 정보가 존재하지 않습니다.");
        }

        ApproveLineDto approveLineDto = new ApproveLineDto();
        return approveLineDto.toDto(backReason);
    }
	
	
	
}
