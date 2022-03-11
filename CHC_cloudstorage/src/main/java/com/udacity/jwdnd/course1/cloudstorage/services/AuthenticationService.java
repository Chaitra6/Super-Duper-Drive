package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
public class AuthenticationService implements AuthenticationProvider{

    private UserMapper usrmapper;
    private HashService hshService;

    public AuthenticationService(UserMapper usrmapper, HashService hshService){
        this.usrmapper = usrmapper;
        this.hshService = hshService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String usrname = authentication.getName();
        String pass = authentication.getCredentials().toString();

        // Gets user by Username from DB, If Exists.
        User user = usrmapper.getUser(usrname);

        if (user != null) {
            String encodedSalt = user.getSalt();
            String hashedPassword = hshService.getHashedValue(pass, encodedSalt);
            if (user.getPassword().equals(hashedPassword)) {
                return new UsernamePasswordAuthenticationToken(usrname, pass, new ArrayList<>());
            }
        }

        // If User Not Found
        return null;
    }

    @Override
    public boolean supports(Class<?> authClass) {

        return authClass.equals(UsernamePasswordAuthenticationToken.class);


    }

}
