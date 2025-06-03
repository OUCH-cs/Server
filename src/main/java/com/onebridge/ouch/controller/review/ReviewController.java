package com.onebridge.ouch.controller.review;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.dto.review.ReviewAverageResponse;
import com.onebridge.ouch.dto.review.ReviewRequest;
import com.onebridge.ouch.dto.review.ReviewResponse;
import com.onebridge.ouch.dto.review.ReviewStatsResponse;
import com.onebridge.ouch.security.authorization.UserId;
import com.onebridge.ouch.service.review.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "리뷰 API", description = "리뷰 CRUD API")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@Operation(summary = "리뷰 작성 API", description = "리뷰 생성 API 입니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createReview(
		@RequestBody @Validated ReviewRequest request,
		@UserId Long userId
	) {
		reviewService.createReview(userId, request);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "(테스트용)특정 리뷰 조회 API", description = "리뷰ID로 특정 리뷰를 조회하는 API입니다.")
	@GetMapping("/{reviewId}")
	public ResponseEntity<ReviewResponse> getReview(@PathVariable("reviewId") Long reviewId) {
		ReviewResponse response = reviewService.getReview(reviewId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "(테스트용)모든 리뷰 조회 API", description = "모든 리뷰를 조회합니다.")
	@GetMapping
	public ResponseEntity<List<ReviewResponse>> getAllReviews() {
		List<ReviewResponse> list = reviewService.getAllReviews();
		return ResponseEntity.ok(list);
	}

	// 4) 특정 병원(ykiho) 리뷰 목록 조회
	@Operation(summary = "특정 병원 리뷰 조회 API", description = "병원 Id를 활용하여 특정 병원의 모든 리뷰를 조회합니다.")
	@GetMapping("/hospitals/{ykiho}")
	public ResponseEntity<List<ReviewResponse>> getReviewsByHospital(@PathVariable("ykiho") String ykiho) {
		List<ReviewResponse> list = reviewService.getReviewsByHospital(ykiho);
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "리뷰 수정 API", description = "리뷰 ID로 리뷰를 수정하는 API입니다.")
	@PutMapping("/{reviewId}")
	public ResponseEntity<ApiResponse<Void>> updateReview(
		@PathVariable("reviewId") Long reviewId,
		@RequestBody @Validated ReviewRequest request,
		@UserId Long userId
	) {
		ReviewResponse updated = reviewService.updateReview(reviewId, userId, request);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	// 6) 리뷰 삭제 (JWT에서 추출한 userId로 작성자 검증)
	@Operation(summary = "리뷰 삭제 API", description = "리뷰 ID로 리뷰를 삭제하는 API입니다.")
	@DeleteMapping("/{reivewId}")
	public ResponseEntity<Void> deleteReview(
		@PathVariable("reivewId") Long reviewId,
		@UserId Long userId
	) {
		reviewService.deleteReview(reviewId, userId);
		return ResponseEntity.noContent().build();
	}

	// 7) 내가 쓴 리뷰 목록 조회
	@Operation(summary = "내 리뷰 목록 조회 API", description = "내가 작성한 리뷰 목록을 조회하는 API입니다.")
	@GetMapping("/me")
	public ResponseEntity<List<ReviewResponse>> getMyReviews(
		@UserId Long userId
	) {
		List<ReviewResponse> list = reviewService.getMyReviews(userId);
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "병원 리뷰 평균 점수 조회 API", description = "특정 병원의 리뷰 평균 점수를 조회하는 API입니다.")
	@GetMapping("/hospitals/{ykiho}/reviews/average")
	public ResponseEntity<ReviewAverageResponse> getAverageScore(
		@PathVariable("ykiho") String ykiho
	) {
		ReviewAverageResponse response = reviewService.getAverageScoreByHospital(ykiho);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "병원 리뷰 평균 점수, 리뷰 개수 조회 API", description = "특정 병원의 리뷰 평균 점수 및 리뷰 개수를 조회하는 API입니다.")
	@GetMapping("/hospitals/{ykiho}/reviews/stats")
	public ResponseEntity<ReviewStatsResponse> getReviewStats(
		@PathVariable("ykiho") String ykiho
	) {
		ReviewStatsResponse response = reviewService.getStatsByHospital(ykiho);
		return ResponseEntity.ok(response);
	}
}