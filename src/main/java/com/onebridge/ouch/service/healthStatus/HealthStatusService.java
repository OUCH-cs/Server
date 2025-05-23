package com.onebridge.ouch.service.healthStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.HealthStatusErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.HealthStatusConverter;
import com.onebridge.ouch.domain.HealthStatus;
import com.onebridge.ouch.dto.healthStatus.request.HealthStatusUpdateRequest;
import com.onebridge.ouch.dto.healthStatus.response.GetHealthStatusResponse;
import com.onebridge.ouch.repository.healthStatus.HealthStatusRepository;
import com.onebridge.ouch.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthStatusService {

	private final UserRepository userRepository;
	private final HealthStatusConverter healthStatusConverter;
	private final HealthStatusRepository healthStatusRepository;

	// //건강상태 생성
	// @Transactional
	// public void createHealthStatus(HealthStatusCreateRequest request, Long userId) {
	// 	User user = userRepository.findById(userId)
	// 		.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));
	//
	// 	HealthStatus healthStatus = healthStatusConverter.healthStatusCreateRequestToHealthStatus(request,
	// 		user);
	//
	// 	healthStatusRepository.save(healthStatus);
	// }

	//특정 건강상태 조회
	@Transactional
	public GetHealthStatusResponse getHealthStatus(Long userId) {
		HealthStatus healthStatus = healthStatusRepository.findByUserId(userId)
			.orElseThrow(() -> new OuchException(HealthStatusErrorCode.HEALTH_STATUS_NOT_FOUND));
		return healthStatusConverter.healthStatusToGetHealthStatusResponse(healthStatus);
	}

	/*
	//특정 사용자의 모든 건강상태 조회
	@Transactional
	public List<DateAndDisease> getUsersAllHealthStatus(Long userId) {
		List<HealthStatus> healthStatus = healthStatusRepository.findAllByUserId(userId);
		return healthStatusConverter.healthStatusToGetUsersAllHealthStatusResponse(healthStatus);
	}
	*/ // 유저 한 명당 하나의 건강상태만 보유

	//특정 건강상태 수정
	@Transactional
	public void updateHealthStatus(HealthStatusUpdateRequest request, Long userId) {
		HealthStatus healthStatus = healthStatusRepository.findByUserId(userId)
			.orElseThrow(() -> new OuchException(HealthStatusErrorCode.HEALTH_STATUS_NOT_FOUND));

		healthStatus.updateHealthStatus(request.getDisease(), request.getAllergy(), request.getBloodPressure(),
			request.getBloodSugar(), request.getMedicineHistory());
	}

	// //특정 건강상태 삭제
	// @Transactional
	// public void deleteHealthStatus(Long healthStatusId, Long userId) {
	// 	HealthStatus healthStatus = healthStatusRepository.findByIdAndUserId(healthStatusId, userId)
	// 		.orElseThrow(() -> new OuchException(HealthStatusErrorCode.HEALTH_STATUS_NOT_FOUND));
	//
	// 	healthStatusRepository.delete(healthStatus);
	// }
}
