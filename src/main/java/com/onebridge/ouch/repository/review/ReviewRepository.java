package com.onebridge.ouch.repository.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onebridge.ouch.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	// 특정 병원 리뷰 조회
	List<Review> findAllByHospital_Ykiho(String hospitalYkiho);

	// 특정 사용자(user.id) 기준으로 리뷰 조회
	List<Review> findAllByUser_Id(Long userId);
}