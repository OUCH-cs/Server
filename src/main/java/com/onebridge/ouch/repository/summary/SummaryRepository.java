package com.onebridge.ouch.repository.summary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.Summary;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
}
