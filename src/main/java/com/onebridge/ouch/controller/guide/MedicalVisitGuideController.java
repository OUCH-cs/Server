package com.onebridge.ouch.controller.guide;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.onebridge.ouch.dto.guide.MedicalVisitGuideStep;
import com.onebridge.ouch.service.guide.MedicalVisitGuideService;

@Tag(name = "통역 상황 속 진료 가이드 API", description = "통역 도중 사용하는 Json 형식의 진료 가이드 API 입니다. 전체 조희 기반으로 필요한 항목만 매핑해서 사용하는 식으로 사용하시면 됩니다."
    + " 현재 한국어와 영어를 제공하며, 추후 다국어 지원 예정입니다.")
@RestController
@RequestMapping("/guide")
@RequiredArgsConstructor
public class MedicalVisitGuideController {

    private final MedicalVisitGuideService guideService;

    @Operation(summary = "통역 상황 속 진료 가이드 전체 조회 API", description = "진료 가이드 내용 전체를 Json 형식으로 조회합니다.")
    @GetMapping
    public List<MedicalVisitGuideStep> getAllSteps() {
        return guideService.getAllSteps();
    }

    @Operation(summary = "통역 상황 속 진료 가이드 스탭 별(step=1~5) 제공 API", description = "진료 가이드를 스탭(1~5) 별로 조회합니다.")
    @GetMapping("/{step}")
    public ResponseEntity<MedicalVisitGuideStep> getStep(@PathVariable int step) {
        MedicalVisitGuideStep result = guideService.getStepByNumber(step);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}
