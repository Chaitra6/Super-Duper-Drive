package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class NoteService {

    //CRUD Operation on notes written by user

    private Logger log =  LoggerFactory.getLogger(NoteService.class);

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }



    public int addNote(Note note){

        log.info("Note to be Added is : "+note);
        return noteMapper.insertNote(note);
    }



    public void updateNote(Note note){
        log.info("Note to be Updated is : "+note);
        noteMapper.updateNote(note);
    }



    public List<Note> getAllNotes( int usrID){
        // Get all notes of the user by user ID
        return noteMapper.getAllNotes(usrID);
    }


    //Not Used
    public Note findNote(int noteID){

        return noteMapper.getNote(noteID);
    }




    public void delNote(int noteID){
        log.info("Deleting Note of ID : "+ noteID);

        noteMapper.deleteNote(noteID);
    }
}
