package com.dbdevdeep.approve.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dbdevdeep.approve.domain.Approve;
import com.dbdevdeep.approve.domain.ApproveDto;
import com.dbdevdeep.approve.repository.ApproveRepository;

@Service
public class ApproveService {

	private final ApproveRepository approveRepository;
	
	@Autowired
	public ApproveService(ApproveRepository approveRepository) {
		this.approveRepository = approveRepository;
	}
	
	public List<ApproveDto> selectApproveList(ApproveDto approveDto){
		List<Approve> approveList = approveRepository.findAll();
		
		List<ApproveDto> approveDtoList = new ArrayList<ApproveDto>();
		for(Approve a : approveList) {
			ApproveDto dto = ApproveDto.builder()
					.appro_no(a.getApproNo())
					.emp_id(a.getEmployee().getEmpId())
					.temp_no(a.getTempEdit().getTempNo())
					.dept_code(a.getDepartment().getDeptCode())
					.job_code(a.getJob().getJobCode())
					.appro_time(a.getApproTime())
					.appro_type(a.getApproType())
					.appro_status(a.getApproStatus())
					.appro_title(a.getApproTitle())
					.appro_content(a.getApproContent())
					.build();
			
			approveDtoList.add(dto);
			System.out.println(approveDtoList);
		}
		return approveDtoList;
	}
}
