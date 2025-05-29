package com.onebridge.ouch.service.selfDiagnosis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.dto.selfDiagnosis.response.DiagnosisAlgorithmMapping;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisAlgorithmService {

    private List<DiagnosisAlgorithmMapping> mappings;

    @PostConstruct
    public void init() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = new ClassPathResource("data/diagnosis_algorithm.json").getInputStream();
        mappings = mapper.readValue(input, new TypeReference<>() {});
    }

    public List<DiagnosisAlgorithmMapping> getAll() {
        return mappings;
    }

    public Optional<DiagnosisAlgorithmMapping> findMatch(String lang, String system, String symptom, String condition) {
        return mappings.stream()
            .filter(e -> getByLang(e.getSystem().getKo(), e.getSystem().getEn(), e.getSystem().getZh(), lang).equals(system))
            .filter(e -> getByLang(e.getSymptom().getKo(), e.getSymptom().getEn(), e.getSymptom().getZh(), lang).equals(symptom))
            .filter(e -> {
                if ("three-step".equals(e.getType()) && e.getCondition() != null) {
                    return getByLang(e.getCondition().getKo(), e.getCondition().getEn(), e.getCondition().getZh(), lang).equals(condition);
                }
                return true;
            })
            .findFirst();
    }

    public List<String> getUniqueSystems(String lang) {
        return mappings.stream()
            .map(e -> getByLang(e.getSystem().getKo(), e.getSystem().getEn(), e.getSystem().getZh(), lang))
            .distinct()
            .sorted()
            .toList();
    }

    public List<String> getSymptomsBySystem(String system, String lang) {
        return mappings.stream()
            .filter(e -> getByLang(e.getSystem().getKo(), e.getSystem().getEn(), e.getSystem().getZh(), lang).equals(system))
            .map(e -> getByLang(e.getSymptom().getKo(), e.getSymptom().getEn(), e.getSymptom().getZh(), lang))
            .distinct()
            .sorted()
            .toList();
    }

    public List<String> getConditionsBySystemAndSymptom(String system, String symptom, String lang) {
        List<DiagnosisAlgorithmMapping> matched = mappings.stream()
            .filter(e -> getByLang(e.getSystem().getKo(), e.getSystem().getEn(), e.getSystem().getZh(), lang).equals(system))
            .filter(e -> getByLang(e.getSymptom().getKo(), e.getSymptom().getEn(), e.getSymptom().getZh(), lang).equals(symptom))
            .toList();

        if (matched.isEmpty()) {
            throw new OuchException(CommonErrorCode.SYSTEM_SYMPTOM_NOT_FOUND);
        }

        List<String> conditions = matched.stream()
            .filter(e -> "three-step".equals(e.getType()))
            .map(e -> getByLang(e.getCondition().getKo(), e.getCondition().getEn(), e.getCondition().getZh(), lang))
            .distinct()
            .sorted()
            .toList();

        if (conditions.isEmpty()) {
            throw new OuchException(CommonErrorCode.CONDITION_NOT_AVAILABLE);
        }

        return conditions;
    }

    private String getByLang(String ko, String en, String zn, String languageCode) {
        return switch (languageCode.toLowerCase()) {
            case "ko" -> ko;
            case "en" -> en;
            case "zh" -> zn;
            default -> throw new OuchException(CommonErrorCode.LANGUAGE_NOT_FOUND);
        };
    }
}
