package com.onebridge.ouch.service.department;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebridge.ouch.dto.hospital.response.AllDepartmentResponse;

import jakarta.annotation.PostConstruct;

@Service
public class DepartmentService {
	private List<AllDepartmentResponse> departmentList;

	@PostConstruct
	public void init() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		InputStream input = new ClassPathResource("data/department.json").getInputStream();
		departmentList = mapper.readValue(input, new TypeReference<List<AllDepartmentResponse>>() {});
	}

	public List<AllDepartmentResponse> getAllDepartments() {
		return departmentList;
	}
}
