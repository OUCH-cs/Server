package com.onebridge.ouch.dto.guide;

import lombok.Getter;
import java.util.List;

@Getter
public class MedicalVisitGuideStep {
    private int step;
    private LocalizedText title;
    private String icon;
    private LocalizedText purpose;
    private List<LocalizedText> whatToExpect;
    private List<LocalizedText> keyPhrases;
    private List<LocalizedText> actionGuide;
    private List<LocalizedText> possibleQuestions; // nullable
    private List<LocalizedText> tips;

    @Getter
    public static class LocalizedText {
        private String ko;
        private String en;
        private String zh;
    }
}
