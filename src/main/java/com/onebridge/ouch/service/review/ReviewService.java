package com.onebridge.ouch.service.review;

import com.onebridge.ouch.domain.Hospital;
import com.onebridge.ouch.domain.Review;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.dto.review.ReviewRequest;
import com.onebridge.ouch.dto.review.ReviewResponse;
import com.onebridge.ouch.repository.hospital.HospitalRepository;
import com.onebridge.ouch.repository.review.ReviewRepository;
import com.onebridge.ouch.repository.user.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final HospitalRepository hospitalRepository;
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

	@Transactional
	public ReviewResponse createReview(Long userId, ReviewRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("해당 userId를 찾을 수 없습니다: " + userId));

		Hospital hospital = hospitalRepository.findById(request.getHospitalYkiho())
			.orElseThrow(() -> new EntityNotFoundException("해당 hospitalYkiho를 찾을 수 없습니다: " + request.getHospitalYkiho()));

		Review review = Review.builder()
			.user(user)
			.hospital(hospital)
			.contents(request.getContents())
			.score(request.getScore())
			.imageUrl(request.getImageUrl())
			.build();

		Review saved = reviewRepository.save(review);
		return toDto(saved);
	}

	@Transactional(readOnly = true)
	public ReviewResponse getReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new EntityNotFoundException("해당 reviewId를 찾을 수 없습니다: " + reviewId));
		return toDto(review);
	}

	@Transactional(readOnly = true)
	public List<ReviewResponse> getAllReviews() {
		return reviewRepository.findAll().stream()
			.map(this::toDto)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ReviewResponse> getReviewsByHospital(String hospitalYkiho) {
		return reviewRepository.findAllByHospital_Ykiho(hospitalYkiho).stream()
			.map(this::toDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public ReviewResponse updateReview(Long reviewId, Long userId, ReviewRequest request) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new EntityNotFoundException("해당 reviewId를 찾을 수 없습니다: " + reviewId));

		// 작성자만 수정할 수 있도록 간단 검증
		if (!review.getUser().getId().equals(userId)) {
			throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
		}

		review.setContents(request.getContents());
		review.setScore(request.getScore());
		review.setImageUrl(request.getImageUrl());

		Review updated = reviewRepository.save(review);
		return toDto(updated);
	}

	@Transactional
	public void deleteReview(Long reviewId, Long userId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new EntityNotFoundException("해당 reviewId를 찾을 수 없습니다: " + reviewId));

		// 작성자만 삭제할 수 있도록 간단 검증
		if (!review.getUser().getId().equals(userId)) {
			throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
		}

		reviewRepository.delete(review);
	}

	@Transactional(readOnly = true)
	public List<ReviewResponse> getMyReviews(Long userId) {
		List<Review> reviews = reviewRepository.findAllByUser_Id(userId);
		return reviews.stream()
			.map(this::toDto)
			.collect(Collectors.toList());
	}

	private ReviewResponse toDto(Review review) {
		return ReviewResponse.builder()
			.id(review.getId())
			.userNickname(review.getUser().getNickname())
			.hospitalYkiho(review.getHospital().getYkiho())
			.contents(review.getContents())
			.score(review.getScore())
			.imageUrl(review.getImageUrl())
			.createdAt(review.getCreatedAt().format(dateFormatter))
			.updatedAt(review.getUpdatedAt().format(dateFormatter))
			.build();
	}
}