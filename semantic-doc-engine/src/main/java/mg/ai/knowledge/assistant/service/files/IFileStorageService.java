package mg.ai.knowledge.assistant.service.files;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String saveFile(MultipartFile file);
}
