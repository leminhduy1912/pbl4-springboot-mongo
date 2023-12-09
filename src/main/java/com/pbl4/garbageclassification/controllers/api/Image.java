package com.pbl4.garbageclassification.controllers.api;

import com.pbl4.garbageclassification.services.impl.GetResultAIServer;
import com.pbl4.garbageclassification.services.IImageService;
import com.pbl4.garbageclassification.services.IQueueProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class Image {
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

    @GetMapping("api/v1/imageCount")
    public ResponseEntity<Integer> countQueueMessage() {

        return ResponseEntity.status(HttpStatus.OK).body(queueProcess.countImage());
    }
    @DeleteMapping("api/v1/imageQueue")
    public ResponseEntity<Boolean> delete() {
        return ResponseEntity.status(HttpStatus.OK).body(queueProcess.delete());
    }

    @GetMapping("/api/v1/imgQueueFirst")
    public ResponseEntity<String> receiveImageNameFirst() {
        boolean isCopied = queueProcess.copyingToStorageFolder(queueProcess.fileNameFirst());
        if (isCopied){
            return ResponseEntity.status(HttpStatus.OK).body(queueProcess.fileNameFirst());
        } else {
            return null;
        }

    }
}
