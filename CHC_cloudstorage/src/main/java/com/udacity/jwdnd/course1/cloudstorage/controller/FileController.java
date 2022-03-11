package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@Controller
@RequestMapping("/file")
public class FileController {
    private Logger log = LoggerFactory.getLogger(FileController.class);

    // Autowire File and User Service

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService usrService;


    @GetMapping("/{name}")
    public String getFile(@PathVariable String name, Model model){

        // name taken from Path
        model.addAttribute("file", fileService.findFile(name));

        // Result Page
        return "result";
    }


    // To Download the given File
    @GetMapping("/download/{name}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String name){

        // name taken from Path and Loaded
        Resource file =fileService.loadFile(name);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


    //Delete file by ID
    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable int id, Model model){

        // id taken from Path
        model.addAttribute("successMessage", fileService.deleteFile(id));

        //ResultPage
        return "result";
    }


    // To Add file from Local device
    @PostMapping
    public String insertFile(@RequestParam(value = "fileUpload")MultipartFile file, RedirectAttributes reAttr) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Current User Name, who is Uploading File
        String authPrincipalName = auth.getName();


        // get user by username
        User user = usrService.getUser(authPrincipalName);


        // file is Requested from user by  @RequestParam

        if(!file.isEmpty() && user !=null){
            //Check for file size
            if(file.getSize() > 10000000){
                // Display Error Message
                reAttr.addFlashAttribute("errorMessage", "File Size has exceeded max limit  |  Please Try AGAIN..!");

                //Redirect to Home Page
                return "redirect:/home";
            }

            if(fileService.findFile(file.getOriginalFilename()) != null){
                // Display Error Message
                reAttr.addFlashAttribute("errorMessage", "This file exists. Please check the FIle Name...!");

                //Redirect to Home Page
                return "redirect:/home";
            }

            // Upload the file if everything is Fine!
            try{
                String saveFile = fileService.storeFile(file, user);
                log.info("Saved File : " + saveFile);

                //Display Success Message
                reAttr.addFlashAttribute("successMessage" , "File "+saveFile+" Saved Successfully.");

                //Redirect to Home Page
                return "redirect:/home";

            }catch (Exception ex){
                log.error("Error : " + ex.getCause() + " | Message " + ex.getMessage() );

                // Display Error message if file couldn't be Saved
                reAttr.addFlashAttribute("errorMessage ", " File Not Uploaded... Please try again!");

                // Redirect to home page
                return "redirect:/home" ;
            }
        }


        reAttr.addFlashAttribute("errorMessage","Error while saving the file.  Please try again");
        return "redirect:/home";


    }

}
