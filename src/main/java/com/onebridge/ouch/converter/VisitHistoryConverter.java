package com.onebridge.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.Summary;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.VisitHistory;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryCreateRequest;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryUpdateRequest;
import com.onebridge.ouch.dto.visitHistory.response.DateAndHospital;
import com.onebridge.ouch.dto.visitHistory.response.GetVisitHistoryResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VisitHistoryConverter {

	public GetVisitHistoryResponse visitHistoryToGetVisitHistoryResponse(VisitHistory visitHistory) {
		return new GetVisitHistoryResponse(visitHistory.getId(), visitHistory.getVisitDate().toString(),
			visitHistory.getHospital(),
			visitHistory.getDepartment(), visitHistory.getSymptoms(),
			visitHistory.getSummary().getContents_summary());
	}

	public List<DateAndHospital> visitHistoryToGetUsersAllVisitHistoryResponse(List<VisitHistory> visitHistory) {
		List<DateAndHospital> list = new ArrayList<>();
		for (VisitHistory history : visitHistory) {
			list.add(new DateAndHospital(history.getId(), history.getUpdatedAt().toString(),
				history.getHospital()));
		}
		return list;
	}

	public VisitHistory visitHistoryCreateRequestToVisitHistory(VisitHistoryCreateRequest request, User user,
		Summary summary) {
		return VisitHistory.builder()
			.user(user)
			.visitDate(request.getVisitDate())
			.hospital(request.getVisitingHospital())
			.department(request.getMedicalSubject())
			.symptoms(request.getSymptoms())
			.summary(summary)
			.build();
	}

	public VisitHistory visitHistoryUpdateRequestToVisitHistory(VisitHistory visitHistory,
		VisitHistoryUpdateRequest request, Summary summary) {
		return visitHistory.toBuilder()
			.visitDate(request.getVisitDate())
			.hospital(request.getVisitingHospital())
			.department(request.getMedicalSubject())
			.symptoms(request.getSymptoms())
			.summary(summary)
			.build();
	}
}
