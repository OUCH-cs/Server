package com.onebridge.ouch.repository.selfDiagnosis;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.SelfDiagnosis;

@Repository
public interface SelfDiagnosisRepository extends JpaRepository<SelfDiagnosis, Long> {

	List<SelfDiagnosis> findAllByUserId(Long userId);

	Optional<SelfDiagnosis> findByIdAndUserId(Long id, Long userId);
}
