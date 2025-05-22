package com.onebridge.ouch.repository.hospital;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.mapping.HospitalDepartment;

@Repository
public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {

	List<HospitalDepartment> findByYkiho(String ykiho);
}
