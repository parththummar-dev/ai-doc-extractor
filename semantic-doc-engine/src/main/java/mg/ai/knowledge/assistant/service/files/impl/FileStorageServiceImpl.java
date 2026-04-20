package mg.ai.knowledge.assistant.service.files.impl;

import mg.ai.knowledge.assistant.service.files.IFileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileStorageServiceImpl implements IFileStorageService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @Override
    public String saveFile(MultipartFile file) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            File dest = new File(filePath);

            file.transferTo(dest);

            return filePath;

        } catch (Exception e) {
            throw new RuntimeException("File storage failed", e);
        }
    }
}
