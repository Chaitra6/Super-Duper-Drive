package com.udacity.jwdnd.course1.cloudstorage.config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {

    private String upload_Directory = "./uploads/" ;

    public void setUpload_Directory(String uDir){
        this.upload_Directory = uDir;
    }

    public String getUpload_Directory(){
        return upload_Directory;
    }
}
