package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/home"})
public class HomeController {

    @Autowired
    private CredService credService;

    @Autowired
    private UserService usrService;

    @Autowired
    private FileService fileService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private EncryptionService encryptService;



    @GetMapping
    public String homeView(Model model,RedirectAttributes reAttr, Authentication auth){

        // Get Current User
        String username = auth.getName();
        User user =usrService.getUser(username);


        if(user==null || username==null ){

            // Display Error Message
            reAttr.addFlashAttribute("errorMessage","Please Login....");

            // Redirect to Login page
            return "redirect:/login";

        }else {

            int userId = user.getUserId();
            model.addAttribute("fileslist", fileService.getAllFiles(userId));
            model.addAttribute("noteslist", noteService.getAllNotes(userId));
            model.addAttribute("credentialslist", credService.getCred(userId));
            model.addAttribute("encryptionService", encryptService);


            // Redirect to home page
            return "home";
        }
    }


}
