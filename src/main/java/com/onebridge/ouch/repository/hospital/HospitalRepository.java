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
			" (6371 * acos(cos(radians(:lat)) * cos(radians(h.lat)) " +
			" * cos(radians(h.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(h.lat)))) as distance " +
			"FROM hospital h " +
			"WHERE h.lat IS NOT NULL AND h.lng IS NOT NULL " +
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
			" (6371 * acos(cos(radians(:lat)) * cos(radians(h.lat)) " +
			" * cos(radians(h.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(h.lat)))) as distance " +
			"FROM hospital h " +
			"JOIN hospital_department hd ON h.ykiho = hd.ykiho " +
			"WHERE hd.department_name = :departmentName AND h.lat IS NOT NULL AND h.lng IS NOT NULL " +
			"ORDER BY distance ASC " +
			"LIMIT :limit OFFSET :offset",
		nativeQuery = true)
	List<Object[]> findByDepartmentOrderByDistance(
		@Param("departmentName") String departmentName,
		@Param("lat") double lat,
		@Param("lng") double lng,
		@Param("limit") int limit,
		@Param("offset") int offset
	);
}
