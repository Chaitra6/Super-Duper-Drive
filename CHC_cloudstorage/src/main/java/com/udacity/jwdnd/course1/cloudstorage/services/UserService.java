package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    // import user and hash mappers

    private final UserMapper usrMapper;
    private final HashService hshService;

    public UserService(UserMapper usrMapper, HashService hshService){
        this.usrMapper = usrMapper;
        this.hshService = hshService;
    }


    // Method to create new User
    public int createNewUsr(User usr){
        SecureRandom r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);
        String encodedSlt = Base64.getEncoder().encodeToString(salt);
        String hashedPass = hshService.getHashedValue(usr.getPassword(), encodedSlt);

        return usrMapper.insertUser(new User(null, usr.getUsername(), encodedSlt, hashedPass, usr.getFirstName(), usr.getLastName()));
    }


    // to check if user is available
    public Boolean checkUserAvailable(String usrName){
        return usrMapper.getUser(usrName) == null;
    }

    public User getUser(String usrName){
        User user = usrMapper.getUser(usrName);
        if(user != null){
            return user;
        }
        else {
            return null;
        }
    }

}
