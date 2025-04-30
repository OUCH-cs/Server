package com.onebridge.ouch.repository.language;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
	Optional<Language> findByCode(String code);
}
