package com.onebridge.ouch.controller.selfDiagnosis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisRequest;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisAlgorithmMapping;
import com.onebridge.ouch.service.selfDiagnosis.DiagnosisAlgorithmService;

@Tag(name = "자가진단 알고리즘 API", description = "Json 형식의 자가진단 알고리즘 API 입니다. 전체 조희 기반으로 필요한 항목만 매핑해서 사용하는 식으로 사용하시면 됩니다."
    + " 현재 한국어와 영어를 제공하며, 추후 다국어 지원 예정입니다.")
@RestController
@RequestMapping("/diagnosis-algorithm")
@RequiredArgsConstructor
public class DiagnosisAlgorithmController {

    private final DiagnosisAlgorithmService diagnosisService;

    @Operation(summary = "자가진단 알고리즘 전체 조회 API", description = "자가진단 알고리즘 내용 전체를 Json 형식으로 조회합니다.")
    @GetMapping
    public List<DiagnosisAlgorithmMapping> getAllMappings() {
        return diagnosisService.getAll();
    }

    @Operation(summary = "증상 기반 관련 진료과 찾기 API", description = "1차 System, 2차 Symptom, 3차 Condition(증상따라 없을 수도 있음)을 기반으로 관련 진료과를 찾습니다. ")
    @PostMapping
    public ResponseEntity<DiagnosisAlgorithmMapping> getDiagnosis(@RequestBody DiagnosisRequest request) {
        return diagnosisService.findMatch(
                request.getLanguage(),
                request.getSystem(),
                request.getSymptom(),
                request.getCondition())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
