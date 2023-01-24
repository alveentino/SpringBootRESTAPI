package com.example.alvintino.User;

public class LoginResponse {
    private String username;
    private String accesstoken;

    public LoginResponse(){
    }

    public LoginResponse(String username, String accesstoken) {
        this.username = username;
        this.accesstoken = accesstoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
