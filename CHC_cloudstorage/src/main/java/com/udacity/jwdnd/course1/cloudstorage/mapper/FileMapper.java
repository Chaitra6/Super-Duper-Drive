package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    // Get File Details by File Name
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    //Insert a File
    @Insert("INSERT INTO FILES ( filename, userid, contenttype, filesize, filedata) VALUES(#{fileName}, #{owner},#{contentType},#{fileSize},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFileUrl(File file);


    // Gets all files for current User
    @Select("SELECT * FROM FILES WHERE userid = #{owner}")
    List<File> getAllFiles(int userId);

    // Delete the File
    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer deleteFile(int id);
}