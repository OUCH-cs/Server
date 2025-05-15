package com.onebridge.ouch.service.selfDiagnosis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            .filter(e -> getByLang(e.getSystem().getKo(), e.getSystem().getEn(), lang).equals(system))
            .filter(e -> getByLang(e.getSymptom().getKo(), e.getSymptom().getEn(), lang).equals(symptom))
            .filter(e -> {
                if ("three-step".equals(e.getType()) && e.getCondition() != null) {
                    return getByLang(e.getCondition().getKo(), e.getCondition().getEn(), lang).equals(condition);
                }
                return true;
            })
            .findFirst();
    }

    private String getByLang(String ko, String en, String lang) {
        return "en".equalsIgnoreCase(lang) ? en : ko;
    }
}
