package com.onebridge.ouch.dto.selfDiagnosis.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiagnosisRequest {
    @Schema(description = "언어 설정", example = "en")
    private String language;  // "ko" or "en"

    @Schema(description = "시스템", example = "Digestive")
    private String system;

    @Schema(description = "증상", example = "Diarrhea")
    private String symptom;

    @Schema(example = "null", nullable = true)
    private String condition; // nullable
}
