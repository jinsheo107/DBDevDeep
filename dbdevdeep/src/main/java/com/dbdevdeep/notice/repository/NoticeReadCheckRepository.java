package com.dbdevdeep.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbdevdeep.notice.domain.NoticeReadCheck;

public interface NoticeReadCheckRepository extends JpaRepository<NoticeReadCheck, Long>{
	
	List<NoticeReadCheck> findByEmployee_EmpId(String read_id);
	
	@Query("SELECT nrc FROM NoticeReadCheck nrc WHERE nrc.employee.id = ?1 AND nrc.notice.id = ?2")
    NoticeReadCheck findByEmployeeIdAndNoticeId(String employeeId, Long noticeId);
}
