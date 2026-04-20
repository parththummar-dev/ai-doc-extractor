package mg.ai.knowledge.assistant.controller;

import mg.ai.knowledge.assistant.service.files.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class DocumentController {

    private IDocumentService documentService;

    @Autowired
    public void setDocumentService(IDocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        documentService.uploadFile(file);
        return "File uploaded and processed successfully!";
    }
}
