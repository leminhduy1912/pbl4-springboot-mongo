package com.pbl4.garbageclassification.controllers;

import com.pbl4.garbageclassification.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @Autowired
    private IImageService iImageService;
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
}
