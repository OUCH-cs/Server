package com.onebridge.ouch.repository.Symptom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.Symptom;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {

	boolean existsByName(String name);

	Optional<Symptom> findByName(String name);
}
