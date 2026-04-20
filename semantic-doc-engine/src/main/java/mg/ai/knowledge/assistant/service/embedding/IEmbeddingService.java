package mg.ai.knowledge.assistant.service.embedding;

public interface IEmbeddingService {
    float[] getEmbedding(String text);
}
