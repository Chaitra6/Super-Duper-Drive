package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.config.FileConfig;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileService {

    private Logger log = LoggerFactory.getLogger(FileService.class);

    private final Path fileLoc;

    @Autowired
    private FileMapper fileMapper;


    public FileService(FileConfig fileLoc) {
        this.fileLoc = Paths.get(fileLoc.getUpload_Directory()).toAbsolutePath().normalize();

        try{
            Files.createDirectories(this.fileLoc);
            log.info("File Location :  ", this.fileLoc);
        }catch (Exception ex){
            throw new RuntimeException("Unsuccessful creating folder to store Files :( " + ex.getCause());
        }
    }



    public File findFile(String fName) {


        return fileMapper.getFile(fName);

    }

    public List<File> getAllFiles(int uID){


        return fileMapper.getAllFiles(uID);

    }


    public String storeFile(MultipartFile file, User user) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Log the file details
        log.info("File Name : ", fileName);
        log.info("File SIze : ", file.getSize());
        log.info("File Content Type : ", file.getContentType());

        try {
            Path targetLoc = this.fileLoc.resolve(fileName);
            Files.copy(file.getInputStream(), targetLoc, StandardCopyOption.REPLACE_EXISTING);

            // insert File into FIles Table
            fileMapper.insertFileUrl(new File(null, fileName, file.getContentType(), "" + file.getSize(), user.getUserId(), file.getBytes()));

        } catch (IOException ex) {
            throw new RuntimeException("Could Not Store the File " + fileName + " Try Again..! \n" + ex.getCause());
        }

        return fileName;
    }







    public Resource loadFile(String fName){
        try{
            Path fPath = this.fileLoc.resolve(fName).normalize();
            Resource res = new UrlResource(fPath.toUri());

            if (res.exists()){
                return res;
            }else {
                throw new RuntimeException("File Not Found : "+ fName);
            }
        }catch (MalformedURLException ex){
            throw new RuntimeException(" File Not Found : "+ fName +"\n" + ex.getCause());
        }
    }





    public Integer deleteFile(int fileID) {


        return fileMapper.deleteFile(fileID);
    }

}

















