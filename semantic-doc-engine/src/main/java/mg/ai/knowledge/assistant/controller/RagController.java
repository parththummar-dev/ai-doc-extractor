package mg.ai.knowledge.assistant.controller;

import mg.ai.knowledge.assistant.service.rag.IRagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rag")
public class RagController {

    private IRagService ragService;

    @Autowired
    private void setRagService(IRagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return ragService.ask(question);
    }
}
