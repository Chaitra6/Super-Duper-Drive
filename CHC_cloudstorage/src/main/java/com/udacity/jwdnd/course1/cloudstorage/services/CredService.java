package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CredService {
    private CredentialMapper credMapper;

    public CredService(CredentialMapper credMapper){
        this.credMapper = credMapper ;
    }

    // To create new Credential
    public int createCred(Credential cred){
        return credMapper.insertCred(cred);
    }

    // Update Credential
    public void updateCred(Credential cred){
         credMapper.updateCred(cred);
    }

    // Delete Credential by ID
    public void deleteCred(int id){
        credMapper.deleteCred(id);
    }


    public List<Credential> getCred(int usrID){
        return credMapper.getCred(usrID);
    }

    public Credential getCredentialByCredId(Integer id) {
        return credMapper.getCredential(id);
    }
}
