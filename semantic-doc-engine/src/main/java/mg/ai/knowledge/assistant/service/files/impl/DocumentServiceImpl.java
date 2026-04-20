package mg.ai.knowledge.assistant.service.files.impl;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import mg.ai.knowledge.assistant.service.embedding.IEmbeddingService;
import mg.ai.knowledge.assistant.service.files.IDocumentService;
import mg.ai.knowledge.assistant.service.files.IFileStorageService;
import mg.ai.knowledge.assistant.util.TextSplitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DocumentServiceImpl implements IDocumentService {

    private IFileStorageService fileStorageService;
    private IEmbeddingService embeddingService;
    private EmbeddingStore<TextSegment> vectorStore;

    @Autowired
    public void setFileStorageService(IFileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Autowired
    public void setEmbeddingService(IEmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @Autowired
    public void setVectorStore(EmbeddingStore<TextSegment> vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void uploadFile(MultipartFile file) {

        // 1. Save file
        String path = fileStorageService.saveFile(file);

        // 2. Extract text
        String content;
        try (PDDocument document = PDDocument.load(new File(path))) {

            PDFTextStripper stripper = new PDFTextStripper();
            content = stripper.getText(document);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 3. Chunk text
        List<String> chunks = TextSplitter.split(content, 250);

        // 4. Generate embeddings
        for (String chunk : chunks) {

            if (chunk.trim().isEmpty()) continue;

            TextSegment segment = TextSegment.from(chunk);

            float[] embedding = embeddingService.getEmbedding(chunk);

            vectorStore.add(Embedding.from(embedding), segment);
        }

        System.out.println("Stored embeddings.");
    }
}
