package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;


import java.util.List;



@Mapper
public interface CredentialMapper {

    // Get List of credentials for current user
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
//     #{userId} is taken from below getCredentials(int userId);
    List<Credential> getCred(Integer userId);


    // get gredential by credID
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialsId}")
    Credential getCredential(Integer credentialsId);


    // Insert new Credential, Cred Id is auto generated
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCred(Credential credential);


    // Update the Credential
    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialId}")
    void updateCred(Credential credential);


    // Delete the Credential
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credId}")
    void deleteCred(int credId);
}