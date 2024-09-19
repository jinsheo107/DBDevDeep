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
        ApproveLine backReason = approveLineRepository.backReasonByApproveNo(approNo);

        if (backReason == null) {
            throw new NoSuchElementException("반려 정보가 존재하지 않습니다.");
        }

        ApproveLineDto approveLineDto = new ApproveLineDto();
        return approveLineDto.toDto(backReason);
    }
	
	
	
}
