package com.pbl4.garbageclassification.services.impl;

import com.pbl4.garbageclassification.services.IImageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
@Service
public class ImageStorage implements IImageService {
    private final Path storageFolder = Paths.get(
            "uploads"
    );


    public ImageStorage(){
        try{
            Files.createDirectories(storageFolder);
        }
        catch(IOException exception){
            throw new RuntimeException("Cannot initialize storage",exception);
        }


    }
    private boolean isImageFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String [] {"png","jpg","jpeg","bmp"}).contains(fileExtension.trim().toLowerCase());
    }


    @Override
    public String storeFile(MultipartFile file) {
        try{
            //file empty
            if (file.isEmpty()){
                throw new RuntimeException("Failed to store empty file");
            }
            //is image file ?
            if (!isImageFile(file)){
                throw new RuntimeException("You only can upload img file");
            }
            // file must be < 5mb
            float  fileSizeInMegabyte = file.getSize()/1_000_000.0f;
            System.out.println(fileSizeInMegabyte);
            if (fileSizeInMegabyte > 5.0f){

                throw new RuntimeException("File must be lower than 5Mb");
            }
            //file must be renamed
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-","");
            generatedFileName = generatedFileName+"."+ fileExtension;
            Path destinationPath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if(!destinationPath.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw  new RuntimeException("Cannot store file outside current directory");
            }
            try(InputStream inputStream =file.getInputStream()){
                Files.copy(inputStream,destinationPath, StandardCopyOption.REPLACE_EXISTING);

            }
            return generatedFileName;
        } catch (IOException exception){
            throw  new RuntimeException("Failed to store file",exception);
        }
    }



    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException(
                        "Could not read file: " + fileName);
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }

    @Override
    public void delete(Set<String> fileNames) {
        try {
            for (String fileName : fileNames) {
                Path filePath = storageFolder.resolve(fileName);
                Files.delete(filePath);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Failed to delete file: " + exception.getMessage(), exception);
        }
    }


}
