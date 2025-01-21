package com.hy.ouch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hy.ouch.domain.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}
