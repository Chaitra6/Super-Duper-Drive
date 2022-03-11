package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    //  Here we have only one dependency, So no need of @Autowired
    private final UserService usrService;

    public SignUpController(UserService userService) {
        this.usrService = userService;
    }


    //SignUp Page
    @GetMapping
    public String signupView(){

        return "signup";

    }




    @PostMapping
    public String signUp(@ModelAttribute User user, RedirectAttributes reAttr){
        String err = null;

        if(!usrService.checkUserAvailable(user.getUsername())){
            err = "User Exists";
        }

        // If User Doesn't Exist
        if(err == null){
            // returns positive int num if user is created
            int usr = usrService.createNewUsr(user);

            // Check if User Account is created
            // if not created, int value will be < 0
            if(usr < 0){
                err = "There was a Problem during Sign-Up process. Please Try Again...!";
            }

            //User Account created successfully
            if(err == null){
                reAttr.addFlashAttribute("signupSuccess", true);

                reAttr.addFlashAttribute("successMessage", "Account created Successfully. Login to continue...");

                //Redirect to Login Page
                return "redirect:/login";
            }
        }

        // Display Error Message
        reAttr.addFlashAttribute("signupError", err);

        //Redirect to SignUp Page (Kind of - Page Refresh)
        return "redirect:/signup";



    }

}


















