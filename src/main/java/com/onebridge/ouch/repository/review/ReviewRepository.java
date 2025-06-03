package com.onebridge.ouch.repository.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onebridge.ouch.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	// 특정 병원 리뷰 조회
	List<Review> findAllByHospital_Ykiho(String hospitalYkiho);

	// 특정 사용자(user.id) 기준으로 리뷰 조회
	List<Review> findAllByUser_Id(Long userId);

	// 추가: 특정 병원 리뷰의 평균 score 계산 (null일 경우 리뷰가 없는 상태)
	@Query("SELECT AVG(r.score) FROM Review r WHERE r.hospital.ykiho = :ykiho")
	Double findAverageScoreByHospitalYkiho(@Param("ykiho") String hospitalYkiho);

	// 리뷰 개수 계산
	long countByHospital_Ykiho(String hospitalYkiho);
}