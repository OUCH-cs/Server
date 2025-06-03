package com.onebridge.ouch.repository.hospital;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, String> {

	// 전체 병원 거리순 정렬 (진료과 미입력시)
	@Query(value =
		"SELECT h.ykiho, h.name, h.address, h.tel, h.lat, h.lng, " +
			"h.type, " + // 종별코드명 추가
			"GROUP_CONCAT(DISTINCT hd.department_name) as departments, " + // 진료과 집계
			"(6371 * acos(cos(radians(:lat)) * cos(radians(h.lat)) " +
			"* cos(radians(h.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(h.lat)))) as distance " +
			"FROM hospital h " +
			"LEFT JOIN hospital_department hd ON h.ykiho = hd.ykiho " +
			// 아래 WHERE 절은 동적으로 구성됨 (여기서는 전부 주석/placeholder)
			// "WHERE (조건) "
			"WHERE h.lat IS NOT NULL AND h.lng IS NOT NULL " +
			"AND h.type != '요양병원' " +
			"GROUP BY h.ykiho " +
			"ORDER BY distance ASC " +
			"LIMIT :limit OFFSET :offset",
		nativeQuery = true)
	List<Object[]> findAllOrderByDistance(
		@Param("lat") double lat,
		@Param("lng") double lng,
		@Param("limit") int limit,
		@Param("offset") int offset
	);

	// 진료과 기반 병원 거리순 정렬 (병원-진료과 조인)
	@Query(value =
		"SELECT h.ykiho, h.name, h.address, h.tel, h.lat, h.lng, " +
			"h.type, " +
			"GROUP_CONCAT(DISTINCT hd.department_name) as departments, " +
			"(6371 * acos(cos(radians(:lat)) * cos(radians(h.lat)) " +
			"* cos(radians(h.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(h.lat)))) as distance " +
			"FROM hospital h " +
			"LEFT JOIN hospital_department hd ON h.ykiho = hd.ykiho " +
			"WHERE (:type IS NULL OR h.type = :type " +
			"   OR (:type = '병원' AND h.type != '약국')) " + // 병원이면 약국 제외
			"AND h.type != '요양병원' " +
			"AND (:department1 IS NULL OR hd.department_name IN (:department1, :department2)) " + // 내과+가정의학과 포함
			"AND h.lat IS NOT NULL AND h.lng IS NOT NULL " +
			"AND (:sidoKr IS NULL OR h.sido = :sidoKr) " +
			"GROUP BY h.ykiho " +
			"ORDER BY distance ASC " +
			"LIMIT :limit OFFSET :offset",
		nativeQuery = true)
	List<Object[]> findWithConditionsOrderByDistance(
		@Param("lat") double lat,
		@Param("lng") double lng,
		@Param("type") String type,
		@Param("department1") String department1,
		@Param("department2") String department2,
		@Param("sidoKr") String sidoKr,
		@Param("limit") int limit,
		@Param("offset") int offset
	);
}
