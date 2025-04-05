package com.onebridge.ouch.repository.visitHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.mapping.VisitHistory;

@Repository
public interface VisitHistoryRepository extends JpaRepository<VisitHistory, Long> {
	List<VisitHistory> findAllByUserId(Long userId);
}
