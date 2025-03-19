package com.hy.ouch.repository.medicalHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hy.ouch.domain.MedicalHistory;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
	List<MedicalHistory> findAllByUserId(Long userId);
}
