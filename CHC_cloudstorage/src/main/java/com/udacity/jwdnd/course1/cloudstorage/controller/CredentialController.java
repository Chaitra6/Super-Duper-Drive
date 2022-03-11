package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import java.util.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private Logger log = LoggerFactory.getLogger(CredentialController.class);

    // Autowire Credential Service, User Service, EncryptionService
    @Autowired
    private CredService credService;

    @Autowired
    private UserService usrService;

    @Autowired
    private EncryptionService encryptService;

    private String secretKeyGen(){
        try{
            SecureRandom r = new SecureRandom();
            byte[] key = new byte[16];
            r.nextBytes(key);
            return Base64.getEncoder().encodeToString(key);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return null;
    }



    @PostMapping
    public String create_Update_Cred(Credential cred, Authentication auth, RedirectAttributes attr){

        String secKey = secretKeyGen();

        // Encrypt the given password
        String encryptPass = encryptService.encryptValue(cred.getPassword(), secKey);
        cred.setKey(secKey);
        cred.setPassword(encryptPass);

        //  Create Credential, if credId Doesn't Exist
        if(cred.getCredentialId() == null){
            try{
                String usrName = auth.getName();
                int usrID = usrService.getUser(usrName).getUserId();
                // set user ID found from above
                cred.setUserId(usrID);
                credService.createCred(cred);

                // Flash message which will be shown when you Update any Credential
                attr.addFlashAttribute("successMessage", "Credential Created Successfully");

                // Redirect to home page
                return "redirect:/home";

            }catch (Exception ex){
                log.error("Error : " + ex.getCause() + " | Message " + ex.getMessage() );

                // Display Error message if credential couldn't be Created
                attr.addFlashAttribute("errorMessage ", " Credential creation Unsuccessful... Please try again!");

                // Redirect to home page
                return "redirect:/home" ;
            }

            //  Update Credential, if credId exists
        }else {

            try{
                credService.updateCred(cred);

                attr.addFlashAttribute("successMessage", "Credential Updated Successfully");

                // Redirect to home page
                return "redirect:/home" ;
            }catch (Exception ex){
                log.error("Error : " + ex.getCause() + " | Message " + ex.getMessage() );

                // Display Error message if credential couldn't be Created
                attr.addFlashAttribute("errorMessage ", " Credential Update Unsuccessful... Please try again!");

                // Redirect to home page
                return "redirect:/home" ;
            }

        }
    }


    @GetMapping("/delete/{credentialId}")
    public String deleteCred(@PathVariable int credentialId, RedirectAttributes attr){
        // try to delete the Credential
        try{
            credService.deleteCred(credentialId);
            attr.addFlashAttribute("successMessage", "Credential Deleted Successfully");

            // Redirect to home page
            return "redirect:/home" ;

        }catch (Exception ex){
            log.error("Error : " + ex.getCause() + " | Message " + ex.getMessage() );

            // Display Error message if credential couldn't be Created
            attr.addFlashAttribute("errorMessage ", " Credential Delete Unsuccessful... Please try again!");

            // Redirect to home page
            return "redirect:/home" ;
        }
    }
}
