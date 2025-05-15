package com.onebridge.ouch.dto.guide;

import lombok.Getter;

@Getter
public class OuchGuideEntry {
    private LocalizedText category;
    private LocalizedText question;
    private LocalizedText answer;

    @Getter
    public static class LocalizedText {
        private String ko;
        private String en;
    }
}
