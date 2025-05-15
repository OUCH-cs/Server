package com.onebridge.ouch.service.guide;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.code.error.ErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.dto.guide.OuchGuideEntry;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OuchGuideService {

    private List<OuchGuideEntry> allGuides;

    @PostConstruct
    public void init() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = new ClassPathResource("data/ouch_guide.json").getInputStream();
        allGuides = mapper.readValue(input, new TypeReference<>() {});
    }

    public List<OuchGuideEntry> getAll() {
        return allGuides;
    }

    public List<OuchGuideEntry> getByCategory(String category, String languageCode) {
        return allGuides.stream()
            .filter(e -> {
                String localizedCategory = "en".equalsIgnoreCase(languageCode)
                    ? e.getCategory().getEn()
                    : e.getCategory().getKo();
                return localizedCategory.equalsIgnoreCase(category);
            })
            .collect(Collectors.toList());
    }

    public List<String> getAllCategories(String languageCode) {
        if (!"ko".equalsIgnoreCase(languageCode) && !"en".equalsIgnoreCase(languageCode)) {
            throw new OuchException(CommonErrorCode.LANGUAGE_NOT_FOUND);
        }

        return allGuides.stream()
            .map(e -> "en".equalsIgnoreCase(languageCode)
                ? e.getCategory().getEn()
                : e.getCategory().getKo())
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
}
