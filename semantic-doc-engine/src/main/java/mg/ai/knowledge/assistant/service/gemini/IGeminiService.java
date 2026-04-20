package mg.ai.knowledge.assistant.service.gemini;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.output.Response;

public interface IGeminiService {
    String generateLlmResponse(String prompt);
}
