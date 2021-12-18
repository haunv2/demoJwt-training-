package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load username: " + username);

        // use temporary username: username or admin
        if(username.equalsIgnoreCase("username") || username.equalsIgnoreCase("admin"))
         return new User(username, "$2a$12$0OzKmICYd3DuZ/zQDi0J7eHThEUlCUzZtEYd7J4bkaTpg1r6jr1aq",
                new ArrayList<>());
        else
            throw new UsernameNotFoundException(username+"Not found");
    }
}
