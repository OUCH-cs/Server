package com.onebridge.ouch.controller.selfDiagnosis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.onebridge.ouch.dto.selfDiagnosis.request.DiagnosisAlgorithmRequest;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisAlgorithmMapping;
import com.onebridge.ouch.service.selfDiagnosis.DiagnosisAlgorithmService;

@Tag(name = "자가진단 알고리즘 API", description = "JSON 기반의 자가진단 알고리즘 API입니다. 단계별 선택지(1차 system, 2차 symptom, 3차 condition)를 활용하여 진료과를 추천받을 수 있습니다. "
    + "현재 한국어(ko)와 영어(en)를 지원합니다.")
@RestController
@RequestMapping("/diagnosis-algorithm")
@RequiredArgsConstructor
public class DiagnosisAlgorithmController {

    private final DiagnosisAlgorithmService diagnosisService;

    @Operation(summary = "전체 알고리즘 데이터 조회", description = "모든 자가진단 알고리즘 항목을 JSON 배열로 조회합니다.")
    @GetMapping
    public List<DiagnosisAlgorithmMapping> getAllMappings() {
        return diagnosisService.getAll();
    }

    @Operation(summary = "증상 기반 진료과 추천", description = "1차 System, 2차 Symptom, 3차 Condition을 기반으로 관련 진료과를 추천합니다. Condition은 'type = three-step'일 경우에만 입력합니다.")
    @PostMapping
    public ResponseEntity<DiagnosisAlgorithmMapping> getDiagnosis(@RequestBody DiagnosisAlgorithmRequest request) {
        return diagnosisService.findMatch(
                request.getLanguage(),
                request.getSystem(),
                request.getSymptom(),
                request.getCondition())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "1차 시스템 목록 조회", description = "자가진단 알고리즘에서 사용 가능한 'system' 리스트를 언어 코드 기준으로 조회합니다.")
    @GetMapping("/systems")
    public ResponseEntity<List<String>> getSystems(
        @Parameter(description = "언어 코드 (ko 또는 en)", example = "en")
        @RequestParam(defaultValue = "en") String languageCode) {
        return ResponseEntity.ok(diagnosisService.getUniqueSystems(languageCode));
    }

    @Operation(summary = "2차 증상 목록 조회", description = "선택한 'system'에 해당하는 'symptom' 리스트를 조회합니다.")
    @GetMapping("/symptoms")
    public ResponseEntity<List<String>> getSymptoms(
        @Parameter(description = "언어 코드 (ko 또는 en)", example = "en")
        @RequestParam(defaultValue = "en") String languageCode,

        @Parameter(description = "1차 시스템 이름", example = "Digestive")
        @RequestParam String system
    ) {
        return ResponseEntity.ok(diagnosisService.getSymptomsBySystem(system, languageCode));
    }

    @Operation(summary = "3차 조건 목록 조회", description = "'system'과 'symptom' 조합에 해당하는 'condition' 리스트를 조회합니다. 'three-step' 항목일 경우에만 존재합니다.")
    @GetMapping("/conditions")
    public ResponseEntity<List<String>> getConditions(
        @Parameter(description = "언어 코드 (ko 또는 en)", example = "en")
        @RequestParam(defaultValue = "en") String languageCode,

        @Parameter(description = "1차 시스템 이름", example = "Digestive")
        @RequestParam String system,

        @Parameter(description = "2차 증상 이름", example = "Heartburn")
        @RequestParam String symptom
    ) {
        return ResponseEntity.ok(diagnosisService.getConditionsBySystemAndSymptom(system, symptom, languageCode));
    }
}
