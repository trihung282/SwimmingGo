package com.example.swimtracker.user_manage;

public class URLManage {
    private String mainURL = "https://stapi.pythonanywhere.com";
    private static URLManage ourInstance;

    public static URLManage getInstance() {
        if (ourInstance == null)
            ourInstance = new URLManage();
        return ourInstance;
    }
    public String getMainURL(){
        return mainURL;
    }
}
