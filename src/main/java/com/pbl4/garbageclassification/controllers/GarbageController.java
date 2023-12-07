package com.pbl4.garbageclassification.controllers;

//@RestController
//public class GarbageController {
//    @Autowired
//    private IGarbageService garbageService;
//    @Autowired
//    private GetResultAIServer getResultAIServer;
//@GetMapping("api/v1/garbage")
//    public postGarbage(){
//    return "hello";
//}
//

//
//
//

////
////    @MessageMapping("/getGarbageById")
////    @SendTo("/topic/getGarbageById")
////    public ResponseEntity<ResponeObject> getAll(@PathVariable String id){
////        System.out.print("id"+id);
////        boolean exist = garbageService.isExist(id);
////        if(exist){
////            return ResponseEntity.status(HttpStatus.OK).body(
////                    new ResponeObject(200,"Querry by id successfully",garbageService.findById(id))
////            );
////        }
////        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
////                new ResponeObject(500,"Id does not exist","")
////        );
////
////    }
////    @MessageMapping("/postGarbage")
////    @SendTo("/topic/postGarbage")
////    public String postGarbage(@Payload byte[] imageData) {
////        // Xử lý dữ liệu hình ảnh ở đây
////        System.out.println("Received image data with length: " + imageData);
////
////        // Trả về thông điệp thành công (nếu cần)
////        return "Image data received successfully";
////    }
////
////        @MessageMapping("/postGarbage")
////    @SendTo("/topic/postGarbage")
////    @PostMapping("api/v1/postGarbage")
////    public String postGarbage() {
////        // Xử lý dữ liệu hình ảnh ở đây
////        //System.out.println("Received image data with length: " + img);
////
////        // Trả về thông điệp thành công (nếu cần)
////        return "Image data received successfully";
////    }
////
////
////
////    @PostMapping("/garbage")
////    public String save(@RequestParam("kindOfGarbage") String kindOfGarbage,
////                       @RequestParam("img")MultipartFile[] files){
////        return garbageService.save(kindOfGarbage,files);
////    }
//
////    @MessageMapping("/updateGarbage")
////    @SendTo("/topic/updateGarbage")
////    public ResponseEntity<ResponeObject> update(@RequestParam("kindOfGarbage") String kindOfGarbage,
////                                                @RequestParam("garbageId") String garbageId,
////                                                @RequestParam("numOfBin") String numOfBin,
////                                                @RequestParam("images")MultipartFile[] files){
////        boolean exist = garbageService.isExist(garbageId);
////        if (exist){
////            return ResponseEntity.status(HttpStatus.OK).body(
////                    new ResponeObject(200,"Updated successfully",garbageService.update(garbageId,kindOfGarbage,numOfBin,files)
////            )
////            );
////        }
////        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
////                new ResponeObject(500,"Id does not exist","")
////        );
////    }
//
////        @MessageMapping("/updateGarbage")
////    @SendTo("/topic/updateGarbage")
////            public ResponseEntity<ResponeObject> update(@RequestBody Garbage garbage,@RequestParam MultipartFile [] files){
////        boolean exist = garbageService.isExist(garbage.getGabargeId());
////        if (exist){
////            return ResponseEntity.status(HttpStatus.OK).body(
////                    new ResponeObject(200,"Updated successfully",garbageService.update(garbage,files)
////            )
////            );
////        }
////        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
////                new ResponeObject(500,"Id does not exist","")
////        );
////    }
////    @DeleteMapping("/api/garbage/{id}")
////    public ResponseEntity<ResponeObject> delete(@PathVariable String id){
////        boolean exist = garbageService.isExist(id);
////        if(exist){
////            garbageService.delete(id);
////            return ResponseEntity.status(HttpStatus.OK).body(
////                    new ResponeObject(200,"Deleted successfully","")
////            );
////        }
////        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
////                new ResponeObject(500,"Deleted failed",id)
////        );
////
////
////    }
//}


import com.pbl4.garbageclassification.services.GetResultAIServer;
import com.pbl4.garbageclassification.services.IGarbageService;
import com.pbl4.garbageclassification.services.IImageService;
import com.pbl4.garbageclassification.services.IQueueProcess;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;





@Controller
public class GarbageController {
    @Autowired
    private IImageService imageService;
    @Autowired
    private IQueueProcess queueProcess;
    @Autowired
    private GetResultAIServer getResultAIServer;
    @Autowired
    private IGarbageService garbageService;
//@PostMapping("/img")
//public ResponseEntity<String> receiveImage(@RequestBody MultipartFile imageFile) {
//
//
//    String fileName = imageService.storeFile(imageFile);
//
//    // Return a success response
//    return ResponseEntity.status(HttpStatus.OK).body("Image successfully received and saved.");
//}

    @PostMapping("/img")
    public ResponseEntity<String> receiveImage(@RequestParam("img") MultipartFile imageFile) {
        System.out.println("image receive"+ imageFile);
        Integer numOfFile = queueProcess.countImage();
        if (numOfFile != 0){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Proccessing,please wait and try again .....");
        } else {
            String fileName = queueProcess.storeFile(imageFile);
            //String storeFile = imageService.storeFile(imageFile);
            // Return a success response
            return ResponseEntity.status(HttpStatus.OK).body("Image successfully received and saved.");
        }

    }
    @GetMapping("/result")
    public String index(Model model, HttpSession session) {
        String fileName = (String) session.getAttribute("img");
        System.out.println("img receive = " + fileName);
        model.addAttribute("img", fileName);
        return "classify-garbage";
    }
    @GetMapping("api/v1/result")
    public ResponseEntity<String> getBytesOfImage(@RequestParam String imgName) throws IOException {
        byte[] bytes = queueProcess.readBytesOfFile(imgName);//
        String result = getResultAIServer.callExternalApiWithFormData(bytes);
        System.out.println("result" + result);
        if (result != null){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }

    }
        @PostMapping("/garbage")
    public ResponseEntity<String> save(@RequestParam("kindOfGarbage") String kindOfGarbage,
                                       @RequestParam("img") String fileName){
        System.out.println("img"+fileName);
        System.out.println("kindOfGarbage"+kindOfGarbage);
        String idGarbage = garbageService.save(kindOfGarbage,fileName.trim());
        if (idGarbage != null){
            return ResponseEntity.status(HttpStatus.OK).body(idGarbage);
        } else {
            return null;
        }
    }
}



