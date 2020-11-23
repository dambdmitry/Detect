package edu.lab.controller;

import edu.lab.mq.ConsumerManagement;
import edu.lab.mq.Producer;
import edu.lab.s3.PhotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
    @Autowired
    private PhotoStorage storage;

    @Autowired
    private Producer producer;

    @Autowired
    private ConsumerManagement consumerManager;

    private String nonDetectedPhoto;
    private String detectedPhoto;

    private String urlForSave;

    @GetMapping("/")
    public String mainPage(){
        return "main";
    }

    @GetMapping("/detect")
    public String detectPage(Model model){
        //if(nonDetectedPhoto != null){
            model.addAttribute("nonDetected", nonDetectedPhoto);
        //}
        //if(detectedPhoto != null){
            model.addAttribute("detected", detectedPhoto);
        //}
        //TODO
        model.addAttribute("saveBucket", storage.getBucket());
        model.addAttribute("storage", storage);
        return "detector";
    }

    @PostMapping("/detect")
    public String photoForDetect(Model model, MultipartFile photo, String urlImgForDetect){
        if(urlImgForDetect != null){
            producer.produce(urlImgForDetect);
            detectedPhoto = consumerManager.getConsumerResponse();
            return "redirect:/detect";
        }
        storage.getFileUrl(storage.getBucket(), nonDetectedPhoto);
        nonDetectedPhoto = storage.save(photo);
        return "redirect:/detect";
    }

    @GetMapping("/education")
    public String eduPage(){
        return "education";
    }

}
