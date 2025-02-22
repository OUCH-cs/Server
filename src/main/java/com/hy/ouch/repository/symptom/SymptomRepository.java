package com.hy.ouch.repository.symptom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hy.ouch.domain.Symptom;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {

	boolean existsByName(String name);

	Optional<Symptom> findByName(String name);
}
