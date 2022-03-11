package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class Credential {
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private Integer userId;
    private String password;


    public Credential(String url, String username, String password) {
        this.url=url;
        this.username=username;
        this.password=password;
    }

}