package com.example.dhrumil.healthcare.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dhrumil.healthcare.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayer extends YouTubeBaseActivity {

    private YouTubePlayerView youtube_player;
    private String video_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        getIntentData();
        init();
        initializeYoutubePlayer();
    }

    private void initializeYoutubePlayer() {
        youtube_player.initialize(Config.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if(!wasRestored)
                {
                    youTubePlayer.loadVideo(video_Id);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    private void getIntentData(){
        Intent i = getIntent();
        video_Id = i.getStringExtra(Config.VIDEO_ID);
    }
    private void init()
    {
        youtube_player =(YouTubePlayerView) findViewById(R.id.youtube_player);
    }
}
