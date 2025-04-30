package com.onebridge.ouch.repository.healthStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.HealthStatus;

@Repository
public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {
	List<HealthStatus> findAllByUserId(Long userId);

	Optional<HealthStatus> findByIdAndUserId(Long medicalHistoryId, Long userId);

	Optional<HealthStatus> findByUserId(Long userId);
}
