package com.onebridge.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.MedicalHistory;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryCreateRequest;
import com.onebridge.ouch.dto.medicalHistory.request.MedicalHistoryUpdateRequest;
import com.onebridge.ouch.dto.medicalHistory.response.DateAndDisease;
import com.onebridge.ouch.dto.medicalHistory.response.GetMedicalHistoryResponse;

@Component
public class MedicalHistoryConverter {

	public List<DateAndDisease> medicalHistoryToGetUsersAllMedicalHistoryResponse(
		List<MedicalHistory> medicalHistory) {

		List<DateAndDisease> list = new ArrayList<>();

		for (MedicalHistory history : medicalHistory) {
			list.add(new DateAndDisease(history.getId(), history.getUpdatedAt().toString(), history.getDisease()));
		}

		return list;
	}

	public GetMedicalHistoryResponse medicalHistoryToGetMedicalHistoryResponse(MedicalHistory medicalHistory) {
		return new GetMedicalHistoryResponse(medicalHistory.getId(), medicalHistory.getDisease(),
			medicalHistory.getAllergy(), medicalHistory.getBloodPressure(), medicalHistory.getBloodSugar(),
			medicalHistory.getMedicineHistory());
	}

	public MedicalHistory medicalHistoryCreateRequestToMedicalHistory(MedicalHistoryCreateRequest request, User user) {
		return MedicalHistory.builder()
			.user(user)
			.disease(request.getDisease())
			.allergy(request.getAllergy())
			.bloodPressure(request.getBloodPressure())
			.bloodSugar(request.getBloodSugar())
			.medicineHistory(request.getMedicineHistory())
			.build();
	}

	public MedicalHistory medicalHistoryUpdateRequestToMedicalHistory(MedicalHistory medicalHistory,
		MedicalHistoryUpdateRequest request) {
		return medicalHistory.toBuilder()
			.disease(request.getDisease())
			.allergy(request.getAllergy())
			.bloodPressure(request.getBloodPressure())
			.bloodSugar(request.getBloodSugar())
			.medicineHistory(request.getMedicineHistory())
			.build();
	}
}
