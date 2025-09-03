
package com.ucms.ucmsapi.storage;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Service
public class LocalFileStorage implements FileStorage {

    private final Path root = Paths.get(System.getProperty("user.home"), "ucms-files");

    public LocalFileStorage() {
        try { Files.createDirectories(root); } catch (IOException ignored) {}
    }

    @Override
    public String store(InputStream in, String originalName, long size, String folder) {
        try {
            Path dir = root.resolve(folder == null ? "misc" : folder);
            Files.createDirectories(dir);
            String safeName = originalName == null ? "file" : originalName.replaceAll("\\s+", "_");
            String key = UUID.randomUUID() + "_" + safeName;
            Files.copy(in, dir.resolve(key), StandardCopyOption.REPLACE_EXISTING);
            return dir.getFileName() + "/" + key; // e.g. "notes/uuid_name.pdf"
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public InputStream load(String key) {
        try {
            return Files.newInputStream(root.resolve(key));
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + key, e);
        }
    }

    @Override
    public void delete(String key) {
        try { Files.deleteIfExists(root.resolve(key)); } catch (IOException ignored) {}
    }
}
