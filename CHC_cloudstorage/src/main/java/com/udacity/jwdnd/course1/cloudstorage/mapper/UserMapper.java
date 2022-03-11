package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // gets user by username
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);


    // Insert New User
    @Insert("INSERT INTO USERS(username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
   // user Id generated
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);
}