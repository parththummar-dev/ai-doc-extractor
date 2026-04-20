package mg.ai.knowledge.assistant.service.rag.impl;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import mg.ai.knowledge.assistant.service.embedding.IEmbeddingService;
import mg.ai.knowledge.assistant.service.gemini.IGeminiService;
import mg.ai.knowledge.assistant.service.gemini.impl.GeminiServiceImpl;
import mg.ai.knowledge.assistant.service.rag.IRagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class IRagServiceImpl implements IRagService {

    private EmbeddingStore<TextSegment> vectorStore;

    private IGeminiService geminiService;

    private IEmbeddingService embeddingService;

    @Autowired
    private void setStore(EmbeddingStore<TextSegment> store) {
        this.vectorStore = store;
    }

    @Autowired
    private void setGeminiService(GeminiServiceImpl geminiServiceImpl) {
        this.geminiService = geminiServiceImpl;
    }

    @Autowired
    private void setEmbeddingService(IEmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @Override
    public String ask(String question) {

        var queryEmbedding = embeddingService.getEmbedding(question);

        var matches = vectorStore.findRelevant(Embedding.from(queryEmbedding), 5);

        String context = matches.stream()
                .map(match -> match.embedded().text())
                .collect(Collectors.joining("\n"));

        String prompt = """
                Answer ONLY from the context below:
                %s

                Question: %s
                """.formatted(context, question);

        return geminiService.generateLlmResponse(prompt);
    }
}
