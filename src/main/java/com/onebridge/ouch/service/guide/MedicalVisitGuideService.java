package com.onebridge.ouch.service.guide;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebridge.ouch.dto.guide.MedicalVisitGuideStep;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class MedicalVisitGuideService {

    private List<MedicalVisitGuideStep> steps;

    @PostConstruct
    public void loadGuideSteps() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = new ClassPathResource("data/medical_visit_guide.json").getInputStream();
        steps = mapper.readValue(input, new TypeReference<>() {});
    }

    public List<MedicalVisitGuideStep> getAllSteps() {
        return steps;
    }

    public MedicalVisitGuideStep getStepByNumber(int stepNumber) {
        return steps.stream()
                .filter(step -> step.getStep() == stepNumber)
                .findFirst()
                .orElse(null);
    }
}
