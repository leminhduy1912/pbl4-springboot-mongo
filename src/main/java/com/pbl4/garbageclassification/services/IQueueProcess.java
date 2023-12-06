package com.pbl4.garbageclassification.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface IQueueProcess {
    public String storeFile(MultipartFile file);


    public void delete(Set<String> fileNames);
    public String fileNameFirst();
    public Integer countImage();
    public byte[] readFileContent(String fileName);
    byte[] readBytesOfFile(String fileName);
}
