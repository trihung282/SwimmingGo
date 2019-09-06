package com.example.swimtracker.user_manage;

public class UserProfile {
    private static User user;
    private static String accessToken, identity;
    private static UserProfile ourInstance = new UserProfile();

    public static UserProfile getInstance() {
        if (ourInstance != null)
            ourInstance = new UserProfile();
        return ourInstance;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getIdentity(){ return identity;}

    public void setIdentity(String identity){this.identity = identity;}

    public static String getAccessToken() {
        return accessToken;
    }

    public static User getUser(){
        return user;
    }

    public void getData(User user){
        this.user = user;
    }
}
