package com.example.swimtracker.coach.library_manage;

public class VideoYoutube {
    private String Titile;
    private String Thumbnail;
    private String VideoID;

    public VideoYoutube(String titile, String thumbnail, String videoID) {
        Titile = titile;
        Thumbnail = thumbnail;
        VideoID = videoID;
    }

    public String getTitile() {
        return Titile;
    }

    public void setTitile(String titile) {
        Titile = titile;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getVideoID() {
        return VideoID;
    }

    public void setVideoID(String videoID) {
        VideoID = videoID;
    }
}
