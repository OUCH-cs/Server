package com.hy.ouch.domain.mapping;

import com.hy.ouch.domain.Department;
import com.hy.ouch.domain.Hospital;
import com.hy.ouch.domain.Summary;
import com.hy.ouch.domain.User;
import com.hy.ouch.domain.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class VisitHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String visitDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	private String symptoms;

	@OneToOne(mappedBy = "visitHistory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	// @JoinColumn(name = "summary_id") -> visitHistory 를 삭제할 때(또는 User 를 삭제할 때) 오류 발생
	private Summary summary;

	private void setSummary(Summary summary) {
		this.summary = summary;
	}

	public void assignSummary(Summary summary) {
		this.setSummary(summary);
		summary.setVisitHistory(this);
	}

	//테스트 (이슈 #32)
}
