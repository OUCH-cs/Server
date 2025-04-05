package com.onebridge.ouch.domain;

import com.onebridge.ouch.domain.common.BaseEntity;
import com.onebridge.ouch.domain.mapping.VisitHistory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Summary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "visit_history_id")
	private VisitHistory visitHistory;

	@Column(columnDefinition = "TEXT")
	private String contents;

	@Column(columnDefinition = "TEXT")
	private String contents_summary;

	public void setVisitHistory(VisitHistory visitHistory) {
		this.visitHistory = visitHistory;
	}

}
