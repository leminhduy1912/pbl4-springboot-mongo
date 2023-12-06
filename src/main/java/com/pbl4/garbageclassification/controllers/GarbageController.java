package com.pbl4.garbageclassification.controllers;//package com.pbl4.garbageclassification.controllers;
//
//
//import com.pbl4.garbageclassification.collections.Garbage;
//import com.pbl4.garbageclassification.results.ResponeObject;
//import com.pbl4.garbageclassification.services.GetResultAIServer;
//import com.pbl4.garbageclassification.services.IGarbageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//
//
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
import com.pbl4.garbageclassification.services.IImageService;
import com.pbl4.garbageclassification.services.IQueueProcess;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//@RestController
//public class GarbageController {
//    @Autowired
//    private IGarbageService garbageService;
//
//    @Autowired
//    private GetResultAIServer getResultAIServer;
//    @Autowired
//    private ImageStorage imageStorage;
//
////    @PostMapping("/api/v1/garbage")
////    public String postGarbage(@RequestParam("img") MultipartFile file,
////                              RedirectAttributes redirectAttributes) {
////        try {
////            String imgFileName = imageStorage.storeFile(file);
////            System.out.println("kind" + file);
////            //model.addAttribute("img","http://localhost:8080/api/v1"+imgFileName);
////            redirectAttributes.addFlashAttribute("img", "http://localhost:8080/api/v1"+imgFileName);
////
////            // Chuyển hướng đến trang HTML của Controller
////            //ModelAndView modelAndView = new ModelAndView("forward:/htmlPage");
////            //modelAndView.addObject("img", file);
////            //return modelAndView;
////            //return file.getOriginalFilename();
//////            return "classify-garbage";
////            return "redirect:/";
////        } catch (Exception e) {
////
////            return null;
////        }
////    }
//
//
//    @PostMapping("/api/v1/garbage")
//    public String postGarbage() {
//        return "hello"
//                ;
//    }
//}


@Controller
@SessionAttributes("img")
public class GarbageController {
    @Autowired
    private IImageService imageService;
    @Autowired
    private IQueueProcess queueProcess;
    @Autowired
    private GetResultAIServer getResultAIServer;
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
        String fileName = queueProcess.storeFile(imageFile);
        // Return a success response
        return ResponseEntity.status(HttpStatus.OK).body("Image successfully received and saved.");
    }
    @GetMapping("/result")
    public String index(Model model, HttpSession session) {
        String fileName = (String) session.getAttribute("img");
        System.out.println("img receive = " + fileName);
        model.addAttribute("img", fileName);
        return "classify-garbage";
    }

    @PostMapping("/getResult")
    public String getResult(@RequestParam("img") MultipartFile img) throws IOException {
        System.out.println("json data" + img);
        byte[] bytes = new byte[0];
        return getResultAIServer.callExternalApiWithFormData(bytes);
    }
}



