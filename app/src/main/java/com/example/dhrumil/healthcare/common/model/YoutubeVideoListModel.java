package com.example.dhrumil.healthcare.common.model;

/**
 * Created by Dhrumil on 3/24/2018.
 */

public class YoutubeVideoListModel {
    private String videoId,title,duration;

    public YoutubeVideoListModel() {
    }

    public YoutubeVideoListModel(String videoId, String title, String duration) {
        this.videoId = videoId;
        this.title = title;
        this.duration = duration;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
