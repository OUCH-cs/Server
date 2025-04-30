package com.onebridge.ouch.repository.nation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onebridge.ouch.domain.Language;
import com.onebridge.ouch.domain.Nation;

public interface NationRepository extends JpaRepository<Nation, Long> {
	Optional<Nation> findByCode(String code);
}
