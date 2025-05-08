package com.onebridge.ouch.converter;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.HealthStatus;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.dto.healthStatus.request.HealthStatusUpdateRequest;
import com.onebridge.ouch.dto.healthStatus.response.GetHealthStatusResponse;

@Component
public class HealthStatusConverter {

	/*
	public List<DateAndDisease> healthStatusToGetUsersAllHealthStatusResponse(
		List<HealthStatus> healthStatus) {

		List<DateAndDisease> list = new ArrayList<>();

		for (HealthStatus history : healthStatus) {
			list.add(new DateAndDisease(history.getId(), history.getUpdatedAt().toString(), history.getDisease()));
		}

		return list;
	}
	*/ // 유저 한 명당 건강상태기록 한 개만 보유

	public GetHealthStatusResponse healthStatusToGetHealthStatusResponse(HealthStatus healthStatus) {
		return new GetHealthStatusResponse(healthStatus.getId(), healthStatus.getDisease(),
			healthStatus.getAllergy(), healthStatus.getBloodPressure(), healthStatus.getBloodSugar(),
			healthStatus.getMedicineHistory());
	}

	// public HealthStatus healthStatusCreateRequestToHealthStatus(HealthStatusCreateRequest request, User user) {
	// 	return HealthStatus.builder()
	// 		.user(user)
	// 		.disease(request.getDisease())
	// 		.allergy(request.getAllergy())
	// 		.bloodPressure(request.getBloodPressure())
	// 		.bloodSugar(request.getBloodSugar())
	// 		.medicineHistory(request.getMedicineHistory())
	// 		.build();
	// }

	public HealthStatus createHealthStatus(User user) {
		return HealthStatus.builder()
			.user(user)
			.disease(null)
			.allergy(null)
			.bloodPressure(null)
			.bloodSugar(null)
			.medicineHistory(null)
			.build();
	}

	public HealthStatus healthStatusUpdateRequestToHealthStatus(HealthStatus healthStatus,
		HealthStatusUpdateRequest request) {
		return healthStatus.toBuilder()
			.disease(request.getDisease())
			.allergy(request.getAllergy())
			.bloodPressure(request.getBloodPressure())
			.bloodSugar(request.getBloodSugar())
			.medicineHistory(request.getMedicineHistory())
			.build();
	}
}
