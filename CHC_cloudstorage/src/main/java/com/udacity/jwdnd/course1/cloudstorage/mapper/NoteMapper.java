package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

        // Get note by note ID
        @Select("SELECT * FROM notes WHERE noteid = #{noteId}")
        Note getNote(Integer noteId);

        // Insert a new Note. Note-ID is generated
        @Insert("INSERT INTO notes ( notetitle,notedescription,userid) VALUES(#{noteTitle}, #{noteDescription},#{userId})")
        @Options(useGeneratedKeys = true, keyProperty = "noteId")
        int insertNote(Note note);

        // Gets all notes in a list, fo the curraent user
        @Select("SELECT * FROM notes WHERE userid = #{userId}")
        List<Note> getAllNotes(int userId);

        // Updates Note details
        @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
        void updateNote(Note note);


        // Deletes Note by Note ID
        @Delete("DELETE FROM notes WHERE noteid = #{noteId}")
        Integer deleteNote(int noteId);
}