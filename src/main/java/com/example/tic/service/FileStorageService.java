package com.example.tic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file, String subFolder) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String safeOriginal = (file.getOriginalFilename() == null ? "file" : file.getOriginalFilename().replaceAll("[\\/]+", "_"));
        String fileName = UUID.randomUUID() + "_" + safeOriginal;

        File dir = new File(uploadDir + "/" + subFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File dest = new File(dir, fileName);
        file.transferTo(dest);

        return subFolder + "/" + fileName;
    }
}
