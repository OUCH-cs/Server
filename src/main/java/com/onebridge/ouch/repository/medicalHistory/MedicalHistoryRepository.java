package com.onebridge.ouch.repository.medicalHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.MedicalHistory;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
	List<MedicalHistory> findAllByUserId(Long userId);

	Optional<MedicalHistory> findByIdAndUserId(Long medicalHistoryId, Long userId);
}
