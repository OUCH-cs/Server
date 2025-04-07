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
import com.onebridge.ouch.dto.medicalHistory.response.MedicalHistoryCreateResponse;
import com.onebridge.ouch.dto.medicalHistory.response.MedicalHistoryUpdateResponse;

@Component
public class MedicalHistoryConverter {

	public MedicalHistoryCreateResponse medicalHistory2MedicalHistoryResponse(MedicalHistory medicalHistory,
		Long userId) {
		return new MedicalHistoryCreateResponse(medicalHistory.getId(),
			medicalHistory.getDisease(),
			medicalHistory.getAllergy(),
			medicalHistory.getBloodPressure(), medicalHistory.getBloodSugar(), medicalHistory.getMedicineHistory());
	}

	public List<DateAndDisease> medicalHistory2GetUsersAllMedicalHistoryResponse(
		List<MedicalHistory> medicalHistory) {

		List<DateAndDisease> list = new ArrayList<>();

		for (MedicalHistory history : medicalHistory) {
			list.add(new DateAndDisease(history.getId(), history.getUpdatedAt().toString(), history.getDisease()));
		}

		return list;
	}

	public GetMedicalHistoryResponse medicalHistory2GetMedicalHistoryResponse(MedicalHistory medicalHistory) {
		return new GetMedicalHistoryResponse(medicalHistory.getId(), medicalHistory.getDisease(),
			medicalHistory.getAllergy(), medicalHistory.getBloodPressure(), medicalHistory.getBloodSugar(),
			medicalHistory.getMedicineHistory());
	}

	public MedicalHistoryUpdateResponse medicalHistory2MedicalHistoryUpdateResponse(MedicalHistory medicalHistory) {
		return new MedicalHistoryUpdateResponse(medicalHistory.getId(), medicalHistory.getDisease(),
			medicalHistory.getAllergy(), medicalHistory.getBloodPressure(), medicalHistory.getBloodSugar(),
			medicalHistory.getMedicineHistory());
	}

	public MedicalHistory medicalHistoryCreateRequest2MedicalHistory(MedicalHistoryCreateRequest request, User user) {
		return MedicalHistory.builder()
			.user(user)
			.disease(request.getDisease())
			.allergy(request.getAllergy())
			.bloodPressure(request.getBloodPressure())
			.bloodSugar(request.getBloodSugar())
			.medicineHistory(request.getMedicineHistory())
			.build();
	}

	public MedicalHistory medicalHistoryUpdateRequest2MedicalHistory(MedicalHistory medicalHistory,
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
