package com.dbdevdeep.approve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.approve.domain.Approve;
import com.dbdevdeep.approve.domain.Reference;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long>{

	List<Reference> findByApprove(Approve approve);
}
