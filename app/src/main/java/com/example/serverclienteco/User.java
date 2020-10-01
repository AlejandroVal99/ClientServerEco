package com.example.serverclienteco;

import java.util.UUID;

public class User {

    private String username, password,id;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
