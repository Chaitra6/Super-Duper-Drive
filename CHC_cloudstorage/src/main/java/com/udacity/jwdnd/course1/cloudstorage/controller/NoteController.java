package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private Logger log = LoggerFactory.getLogger(NoteController.class);

    // Autowire User and Note service
    @Autowired
    private UserService usrService;

    @Autowired
    private NoteService noteService;



    // Get Notes by user ID
    @GetMapping
    public List<Note> getAllNotes(int uId){
        return noteService.getAllNotes(uId);
    }

    // Adding a Note
    @PostMapping
    public String insertNote(Authentication auth , Note note, RedirectAttributes reAttr)
    {
        //  Get curr user name
        String  userName = auth.getName();

        // get user by username
        User user = usrService.getUser(userName);

        // Check if note already exist
        if(note.getNoteId() != null ) {
            // Note Exists, SO Update the note
                try {
                    noteService.updateNote(note);

                    // Display Success Message
                    reAttr.addFlashAttribute("successMessage", "Note Updated Successfully..!");

                    // Redirect to HomePage
                    return "redirect:/home";

                } catch (Exception ex) {
                    log.error("Error : " + ex.getCause() + ". Message : " + ex.getMessage());

                    //Display Error Message
                    reAttr.addFlashAttribute("errorMessage", "Note Update Unsuccessful.   Please try again...!");

                    // Redirect to HomePage
                    return "redirect:/home";
                }
            // Note doesn't exist, So Insert Note as new note
           }else{
                try{
                    note.setUserId(user.getUserId());
                    noteService.addNote(note);

                    // Display Success Message
                    reAttr.addFlashAttribute("successMessage", "Note Added Successfully..!");

                    // Redirect to HomePage
                    return "redirect:/home";
                }catch (Exception ex){
                    log.error("Error : " + ex.getCause() + ". Message : " + ex.getMessage());

                    //Display Error Message
                    reAttr.addFlashAttribute("errorMessage", " Unsuccessful adding Note.   Please try again...!");

                    // Redirect to HomePage
                    return "redirect:/home";
                }
           }//else
    }



    // Deleting Note
    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable int noteId, RedirectAttributes reAttr) {
        // noteId taken from Path
        try {
            noteService.delNote(noteId);

            // Display Success Message
            reAttr.addFlashAttribute("successMessage", "Note Deleted Successfully..!");

            // Redirect to HomePage
            return "redirect:/home";

        } catch (Exception ex) {
            log.error("Error : " + ex.getCause() + ". Message : " + ex.getMessage());

            //Display Error Message
            reAttr.addFlashAttribute("errorMessage", "Unsuccessful Note Deletion.  Please try again!");

            // Redirect to HomePage
            return "redirect:/home";
        }
    }





}
