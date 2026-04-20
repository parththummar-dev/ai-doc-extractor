package mg.ai.knowledge.assistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentChunk {
    private String text;
    private float[] embedding;
}
