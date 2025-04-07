package com.onebridge.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.apiPayload.code.error.VisitHistoryErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.domain.Summary;
import com.onebridge.ouch.domain.mapping.VisitHistory;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryCreateRequest;
import com.onebridge.ouch.dto.visitHistory.request.VisitHistoryUpdateRequest;
import com.onebridge.ouch.dto.visitHistory.response.DateAndHospital;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryCreateResponse;
import com.onebridge.ouch.dto.visitHistory.response.VisitHistoryUpdateResponse;
import com.onebridge.ouch.repository.department.DepartmentRepository;
import com.onebridge.ouch.repository.hospital.HospitalRepository;
import com.onebridge.ouch.repository.user.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VisitHistoryConverter {

	private final HospitalRepository hospitalRepository;
	private final DepartmentRepository departmentRepository;
	private final UserRepository userRepository;

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

	public VisitHistory visitHistoryCreateRequest2VisitHistory(VisitHistoryCreateRequest request, Long userId) {
		return VisitHistory.builder()
			.user(userRepository.findById(userId)
				.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.USER_NOT_FOUND)))
			.visitDate(request.getVisitDate())
			.hospital(hospitalRepository.findByName(request.getVisitingHospital())
				.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.HOSPITAL_NOT_FOUND)))
			.department(departmentRepository.findByName(request.getMedicalSubject())
				.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.DEPARTMENT_NOT_FOUND)))
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
		VisitHistory visitHistory) {
		return visitHistory.toBuilder()
			.visitDate(request.getVisitDate())
			.hospital(hospitalRepository.findByName(request.getVisitingHospital())
				.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.HOSPITAL_NOT_FOUND)))
			.department(departmentRepository.findByName(request.getMedicalSubject())
				.orElseThrow(() -> new OuchException(VisitHistoryErrorCode.DEPARTMENT_NOT_FOUND)))
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
