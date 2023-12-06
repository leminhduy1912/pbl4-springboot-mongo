package com.pbl4.garbageclassification.controllers;

import com.pbl4.garbageclassification.services.GetResultAIServer;
import com.pbl4.garbageclassification.services.IImageService;
import com.pbl4.garbageclassification.services.IQueueProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
public class ImageController {
    @Autowired
    private IImageService iImageService;
    @Autowired
    private IQueueProcess queueProcess;
    @Autowired
    private GetResultAIServer getResultAIServer;
    @GetMapping("api/v1/image/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailsFile(@PathVariable String fileName){
        try{
            byte[] bytes = iImageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }
        catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping("api/v1/imageQueueFirst/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailsFirstFile(@PathVariable String fileName){
        try{
            byte[] bytes = queueProcess.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }
        catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping("api/v1/imageCount")
    public ResponseEntity<Integer> countQueueMessage() {

        return ResponseEntity.status(HttpStatus.OK).body(queueProcess.countImage());
    }
    @GetMapping("api/v1/imageBytes")
    public ResponseEntity<byte[]> getBytesOfImage(@RequestParam String imgName) throws IOException {
        byte[] bytes = queueProcess.readBytesOfFile(imgName);
        String result = getResultAIServer.callExternalApiWithFormData(bytes);
        System.out.println("result" + result);
        return ResponseEntity.status(HttpStatus.OK).body(queueProcess.readBytesOfFile(imgName));
    }
    @GetMapping("/api/v1/imgQueueFirst")
    public ResponseEntity<String> receiveImageNameFirst() {
        return ResponseEntity.status(HttpStatus.OK).body(queueProcess.fileNameFirst());
    }
}
