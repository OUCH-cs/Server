package com.onebridge.ouch.dto.selfDiagnosis.response;

import lombok.Getter;
import java.util.List;

@Getter
public class DiagnosisAlgorithmMapping {
    private String type;
    private DiagnosisAlgorithmSystem system;
    private DiagnosisAlgorithmSymptom symptom;
    private DiagnosisAlgorithmCondition condition; // nullable
    private List<DiagnosisAlgorithmDepartment> departments;
    private DiagnosisAlgorithmNote note;

    @Getter
    public static class DiagnosisAlgorithmSystem {
        private String ko;
        private String en;
        private String zh;
    }

    @Getter
    public static class DiagnosisAlgorithmSymptom {
        private String ko;
        private String en;
        private String zh;
    }

    @Getter
    public static class DiagnosisAlgorithmCondition {
        private String ko;
        private String zh;
        private String en;
    }

    @Getter
    public static class DiagnosisAlgorithmDepartment {
        private String ko;
        private String zh;
        private String en;
    }

    @Getter
    public static class DiagnosisAlgorithmNote {
        private String ko;
        private String zh;
        private String en;
    }
}
