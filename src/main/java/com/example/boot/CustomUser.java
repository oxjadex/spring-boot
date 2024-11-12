package com.example.boot;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUser extends User {
    public Integer id;
    public String displayName;

    public CustomUser(String username, String password, List<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}