package mg.ai.knowledge.assistant.service.embedding.impl;

import mg.ai.knowledge.assistant.service.embedding.IEmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmbeddingServiceImpl implements IEmbeddingService {

    private RestTemplate restTemplate;

    @Value("${embedding.service.url}")
    private String embeddingBaseUrl;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public float[] getEmbedding(String text) {
        Map<String, String> request = Map.of("text", text);

        Map response = restTemplate.postForObject(embeddingBaseUrl, request, Map.class);

        var list = (java.util.List<Double>) response.get("embedding");

        float[] embedding = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            embedding[i] = list.get(i).floatValue();
        }

        return embedding;
    }
}
