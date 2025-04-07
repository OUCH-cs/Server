package com.onebridge.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.Department;
import com.onebridge.ouch.domain.Hospital;
import com.onebridge.ouch.domain.Summary;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.VisitHistory;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryCreateRequest;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryUpdateRequest;
import com.onebridge.ouch.dto.visitHistory.response.DateAndHospital;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryCreateResponse;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryUpdateResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VisitHistoryConverter {

	public VisitHistoryCreateResponse visitHistory2VisitHistoryCreateResponse(VisitHistory visitHistory) {
		return new VisitHistoryCreateResponse(visitHistory.getId(), visitHistory.getVisitDate(),
			visitHistory.getHospital().getName(),
			visitHistory.getDepartment().getName(), visitHistory.getSymptoms(),
			visitHistory.getSummary().getContents());
	}

	public VisitHistoryUpdateResponse visitHistory2GetVisitHistoryResponse(VisitHistory visitHistory) {
		return new VisitHistoryUpdateResponse(visitHistory.getId(), visitHistory.getVisitDate(),
			visitHistory.getHospital().getName(),
			visitHistory.getDepartment().getName(), visitHistory.getSymptoms(),
			visitHistory.getSummary().getContents());
	}

	public List<DateAndHospital> visitHistory2GetUsersAllVisitHistoryResponse(List<VisitHistory> visitHistory) {
		List<DateAndHospital> list = new ArrayList<>();
		for (VisitHistory history : visitHistory) {
			list.add(new DateAndHospital(history.getId(), history.getUpdatedAt().toString(),
				history.getHospital().getName()));
		}
		return list;
	}

	public VisitHistoryUpdateResponse visitHistory2VisitHistoryUpdateResponse(VisitHistory visitHistory) {
		return new VisitHistoryUpdateResponse(visitHistory.getId(), visitHistory.getVisitDate(),
			visitHistory.getHospital().getName(),
			visitHistory.getDepartment().getName(), visitHistory.getSymptoms(),
			visitHistory.getSummary().getContents());
	}

	public VisitHistory visitHistoryCreateRequest2VisitHistory(VisitHistoryCreateRequest request, User user,
		Hospital hospital, Department department) {
		return VisitHistory.builder()
			.user(user)
			.visitDate(request.getVisitDate())
			.hospital(hospital)
			.department(department)
			.symptoms(request.getSymptoms())
			.build();
	}

	public Summary visitHistoryCreateRequest2Summary(VisitHistoryCreateRequest request, VisitHistory visitHistory) {
		return Summary.builder()
			.visitHistory(visitHistory)
			.contents(request.getTreatmentSummary())
			.contents_summary(request.getTreatmentSummary())
			.build();
	}

	public VisitHistory visitHistoryUpdateRequest2VisitHistory(VisitHistoryUpdateRequest request,
		VisitHistory visitHistory, Hospital hospital, Department department) {
		return visitHistory.toBuilder()
			.visitDate(request.getVisitDate())
			.hospital(hospital)
			.department(department)
			.symptoms(request.getSymptoms())
			.build();
	}

	public Summary visitHistoryUpdateRequest2Summary(VisitHistoryUpdateRequest request,
		VisitHistory updatedVisitHistory, Summary summary) {
		return summary.toBuilder()
			.visitHistory(updatedVisitHistory)
			.contents(request.getTreatmentSummary())
			.contents_summary(request.getTreatmentSummary())
			.build();
	}

}
