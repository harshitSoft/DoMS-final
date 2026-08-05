package com.doms.doms.serviceImpl;

import com.doms.doms.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadPath = Paths.get("uploads");

    public FileStorageServiceImpl() {
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory.", e);
        }
    }

    @Override
    public String saveFile(MultipartFile file) {

        try {

            String originalFileName = file.getOriginalFilename();

            String uniqueFileName =
                    UUID.randomUUID() + "_" + originalFileName;

            Path targetLocation = uploadPath.resolve(uniqueFileName);

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return targetLocation.toString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public Resource downloadFile(String filePath) {

        try {

            Path path = Paths.get(filePath);

            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return resource;
            }

            throw new RuntimeException("File not found.");

        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid file path.", e);
        }
    }

    @Override
    public Resource viewFile(String filePath) {
        return downloadFile(filePath);
    }

    @Override
    public void deleteFile(String filePath) {

        try {

            Path path = Paths.get(filePath);

            Files.deleteIfExists(path);

        } catch (IOException e) {
            throw new RuntimeException("Unable to delete file.", e);
        }
    }
}