package com.onebridge.ouch.repository.medicalRecord;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.mapping.MedicalRecord;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
	List<MedicalRecord> findAllByUserId(Long userId);

	Optional<MedicalRecord> findByIdAndUserId(Long visitHistoryId, Long userId);
}
