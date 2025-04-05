package com.onebridge.ouch.repository.hospital;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

	Optional<Hospital> findByName(String name);
}
