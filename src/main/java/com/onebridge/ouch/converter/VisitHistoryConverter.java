package com.onebridge.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.mapping.VisitHistory;
import com.onebridge.ouch.dto.visitHistory.response.DateAndHospital;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryCreateResponse;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryUpdateResponse;

@Component
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

}
