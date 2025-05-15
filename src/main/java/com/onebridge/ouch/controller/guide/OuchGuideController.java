package com.onebridge.ouch.controller.guide;

import com.onebridge.ouch.dto.guide.OuchGuideEntry;
import com.onebridge.ouch.service.guide.OuchGuideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ouch Guide API", description = "홈 화면에서 접근하는 Ouch Guide API입니다. 한국 의료 시스템이나 Ouch 서비스에 대해 자주 묻는 질문과 답변을 제공합니다. 카테고리별 필터링도 가능하며, 현재 한국어와 영어를 지원합니다.")
@RestController
@RequestMapping("/guide/ouch")
@RequiredArgsConstructor
public class OuchGuideController {

    private final OuchGuideService guideService;

    @Operation(summary = "전체 참고 질문/답변 조회", description = "진료 중 자주 묻는 질문 및 답변 전체를 JSON 형태로 조회합니다.")
    @GetMapping
    public List<OuchGuideEntry> getAll() {
        return guideService.getAll();
    }

    @Operation(summary = "카테고리별 참고 질문/답변 조회", description = "카테고리명(한글 또는 영어)과 언어 코드(ko/en)를 기반으로 해당 항목을 필터링하여 조회합니다.")
    @GetMapping("/filter")
    public ResponseEntity<List<OuchGuideEntry>> getByCategory(
        @Parameter(description = "카테고리 이름 (예: 보험 및 비용 / Insurance & Costs", example = "Insurance & Costs")
        @RequestParam String category,

        @Parameter(description = "언어 코드 (ko 또는 en)", example = "en")
        @RequestParam String languageCode
    ) {
        List<OuchGuideEntry> result = guideService.getByCategory(category, languageCode);
        return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

    @Operation(summary = "카테고리 목록 조회", description = "언어(ko/en) 기준으로 카테고리 이름 목록을 조회합니다.")
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories(
        @RequestParam(name = "languageCode", defaultValue = "en") String languageCode
    ) {
        return ResponseEntity.ok(guideService.getAllCategories(languageCode));
    }
}
