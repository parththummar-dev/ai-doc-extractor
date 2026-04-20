package mg.ai.knowledge.assistant.service.gemini.impl;

import mg.ai.knowledge.assistant.service.gemini.IGeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiServiceImpl implements IGeminiService {

    @Value("${gemini.api.key}")
    private String API_KEY;

    @Value("${gemini.api.url}")
    private String API_URL;

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String generateLlmResponse(String prompt) {

        Map<String, Object> textPart = Map.of("text", prompt);
        Map<String, Object> part = Map.of("parts", List.of(textPart));
        Map<String, Object> requestBody = Map.of("contents", List.of(part));

        ResponseEntity<Map> response = callGeminiAPI(requestBody);

        return processResponse(response);
    }

    private ResponseEntity<Map> callGeminiAPI(Map<String, Object> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", API_KEY);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity(API_URL, request, Map.class);
    }

    private String processResponse(ResponseEntity<Map> response) {
        try {
            List candidates = (List) response.getBody().get("candidates");
            Map firstCandidate = (Map) candidates.get(0);
            Map content = (Map) firstCandidate.get("content");
            List parts = (List) content.get("parts");
            Map textMap = (Map) parts.get(0);

            return textMap.get("text").toString();

        } catch (Exception e) {
            System.out.println("Error parsing Gemini response: " + e.getMessage());
            return "Error parsing response";
        }
    }
}
