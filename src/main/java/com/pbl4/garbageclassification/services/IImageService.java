package com.pbl4.garbageclassification.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IImageService {
    public String storeFile(MultipartFile file);
    public Stream<Path> loadAll();
    public byte[] readFileContent(String fileName);
    public void deleteALlFiles();
}
