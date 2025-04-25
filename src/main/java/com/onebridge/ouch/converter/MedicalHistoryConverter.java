package com.onebridge.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.HealthStatus;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryCreateRequest;
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryUpdateRequest;
import com.onebridge.ouch.dto.medicalHistory.response.DateAndDisease;
import com.onebridge.ouch.dto.medicalHistory.response.GetMedicalHistoryResponse;

@Component
public class MedicalHistoryConverter {

	public List<DateAndDisease> medicalHistoryToGetUsersAllMedicalHistoryResponse(
		List<HealthStatus> healthStatus) {

		List<DateAndDisease> list = new ArrayList<>();

		for (HealthStatus history : healthStatus) {
			list.add(new DateAndDisease(history.getId(), history.getUpdatedAt().toString(), history.getDisease()));
		}

		return list;
	}

	public GetMedicalHistoryResponse medicalHistoryToGetMedicalHistoryResponse(HealthStatus healthStatus) {
		return new GetMedicalHistoryResponse(healthStatus.getId(), healthStatus.getDisease(),
			healthStatus.getAllergy(), healthStatus.getBloodPressure(), healthStatus.getBloodSugar(),
			healthStatus.getMedicineHistory());
	}

	public HealthStatus medicalHistoryCreateRequestToMedicalHistory(MedicalHistoryCreateRequest request, User user) {
		return HealthStatus.builder()
			.user(user)
			.disease(request.getDisease())
			.allergy(request.getAllergy())
			.bloodPressure(request.getBloodPressure())
			.bloodSugar(request.getBloodSugar())
			.medicineHistory(request.getMedicineHistory())
			.build();
	}

	public HealthStatus medicalHistoryUpdateRequestToMedicalHistory(HealthStatus healthStatus,
		MedicalHistoryUpdateRequest request) {
		return healthStatus.toBuilder()
			.disease(request.getDisease())
			.allergy(request.getAllergy())
			.bloodPressure(request.getBloodPressure())
			.bloodSugar(request.getBloodSugar())
			.medicineHistory(request.getMedicineHistory())
			.build();
	}
}
