package com.dbdevdeep.approve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.approve.domain.VacationRequest;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>{

}
