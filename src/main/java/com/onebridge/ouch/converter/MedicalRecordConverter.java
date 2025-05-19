package com.onebridge.ouch.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.Summary;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.mapping.MedicalRecord;
import com.onebridge.ouch.dto.medicalRecord.request.MedicalRecordCreateRequest;
import com.onebridge.ouch.dto.medicalRecord.request.MedicalRecordUpdateRequest;
import com.onebridge.ouch.dto.medicalRecord.response.DateAndHospital;
import com.onebridge.ouch.dto.medicalRecord.response.GetMedicalRecordResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MedicalRecordConverter {

	public GetMedicalRecordResponse medicalRecordToGetMedicalRecordResponse(MedicalRecord medicalRecord) {
		return new GetMedicalRecordResponse(medicalRecord.getId(), medicalRecord.getVisitDate().toString(),
			medicalRecord.getHospital(),
			medicalRecord.getDepartment(), medicalRecord.getSymptoms(),
			medicalRecord.getSummary().getContents_summary());
	}

	public List<DateAndHospital> medicalRecordToGetUsersAllMedicalRecordResponse(List<MedicalRecord> medicalRecords) {
		List<DateAndHospital> list = new ArrayList<>();
		for (MedicalRecord record : medicalRecords) {
			list.add(new DateAndHospital(record.getId(), record.getUpdatedAt().toString(),
				record.getHospital()));
		}
		return list;
	}

	public MedicalRecord medicalRecordCreateRequestToMedicalRecord(MedicalRecordCreateRequest request, User user,
		Summary summary) {
		return MedicalRecord.builder()
			.user(user)
			.visitDate(request.getVisitDate())
			.hospital(request.getVisitingHospital())
			.department(request.getMedicalSubject())
			.symptoms(request.getSymptoms())
			.summary(summary)
			.build();
	}

	public void medicalRecordUpdateRequestToMedicalRecord(MedicalRecord medicalRecord,
		MedicalRecordUpdateRequest request, Summary summary) {
		medicalRecord.update(
			request.getVisitDate(),
			request.getVisitingHospital(),
			request.getMedicalSubject(),
			request.getSymptoms(),
			summary
		);
	}
}
