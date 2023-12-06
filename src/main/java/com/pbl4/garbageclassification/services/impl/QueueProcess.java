package com.pbl4.garbageclassification.services.impl;

import com.pbl4.garbageclassification.services.IQueueProcess;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
public class QueueProcess implements IQueueProcess
{
    private final Path storageFolder = Paths.get(
            "queue"
    );
    public QueueProcess(){
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

    @Override
    public String fileNameFirst() {
        // Đường dẫn đến thư mục chứa file
        String path = "C:\\Users\\minhd\\OneDrive\\Desktop\\pbl4\\garbage-classification\\queue";

        // Tạo đối tượng File cho thư mục
        File folder = new File(path);

        // Kiểm tra xem thư mục có tồn tại không
        if (folder.exists() && folder.isDirectory()) {
            // Lấy danh sách các tệp trong thư mục
            File[] danhSachTep = folder.listFiles();

            // Kiểm tra xem danh sách tệp có null không và có ít nhất một file không
            if (danhSachTep != null && danhSachTep.length > 0) {
                // Lấy tên của file đầu tiên trong danh sách
                String fileName = danhSachTep[0].getName();

                // In ra tên của file
                System.out.println("Tên của file trong thư mục: " + fileName);
                return fileName;
            } else {
                System.out.println("Thư mục rỗng hoặc không có file.");
                return  null;
            }
        } else {
            System.out.println("Thư mục không tồn tại hoặc không phải là thư mục.");
            return null;
        }
    }

    @Override
    public Integer countImage() {
        String path = "C:\\Users\\minhd\\OneDrive\\Desktop\\pbl4\\garbage-classification\\queue";
        File folder = new File(path);
        int countFile = 0;
        if (folder.exists() && folder.isDirectory()) {
            // Lấy danh sách các tệp trong thư mục
            File[] listFiles = folder.listFiles();

            // Kiểm tra xem danh sách tệp có null không
            if (listFiles != null) {
                for (File file : listFiles) {
                    // Kiểm tra xem từng đối tượng có phải là file không
                    if (file.isFile()) {
                        countFile++;
                    }
                }
                System.out.println("Số lượng file trong thư mục: " + countFile);
                return countFile;
            } else {
                System.out.println("Thư mục rỗng.");
                return 0;
            }
        } else {
            System.out.println("Thư mục không tồn tại hoặc không phải là thư mục.");
            return null;
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
    public byte[] readBytesOfFile(String fileName) {
        // Đường dẫn đến thư mục chứa file
        String pathFolder = "C:\\Users\\minhd\\OneDrive\\Desktop\\pbl4\\garbage-classification\\queue";
        // Tạo đối tượng Path từ đường dẫn
        Path path = Paths.get(pathFolder, fileName.trim());

        try {
            // Đọc toàn bộ nội dung của file thành mảng byte
            byte[] bytes = Files.readAllBytes(path);
            System.out.println("file name"+fileName);
            System.out.println("byte"+bytes);
           return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
