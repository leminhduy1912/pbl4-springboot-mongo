package com.pbl4.garbageclassification.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface IImageService {
    public String storeFile(MultipartFile file);

    public byte[] readFileContent(String fileName);
    public void delete(Set<String> fileNames);

}
