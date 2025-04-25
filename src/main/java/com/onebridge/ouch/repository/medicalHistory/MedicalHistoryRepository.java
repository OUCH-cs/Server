package com.onebridge.ouch.repository.medicalHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.HealthStatus;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<HealthStatus, Long> {
	List<HealthStatus> findAllByUserId(Long userId);

	Optional<HealthStatus> findByIdAndUserId(Long medicalHistoryId, Long userId);
}
