package mg.ai.knowledge.assistant.util;

import java.util.ArrayList;
import java.util.List;

public class TextSplitter {

    public static List<String> split(String text, int chunkSize) {

        List<String> chunks = new ArrayList<>();

        int start = 0;

        while (start < text.length()) {
            int end = Math.min(start + chunkSize, text.length());
            chunks.add(text.substring(start, end));
            start = end;
        }

        return chunks;
    }
}
