package com.example.swimtracker.coach.library_manage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.swimtracker.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    YouTubePlayerView youTubePlayerView;
    String id = "";
    int REQUEST_VIDEO = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.myYouTube);


        Intent intent =getIntent();
        id = intent.getStringExtra("idVideoYouTube");
        youTubePlayerView.initialize(ManageKeyVideo.API_KEY,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(id);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(PlayVideo.this, REQUEST_VIDEO);
        }else {
            Toast.makeText(this, "Video lỗi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_VIDEO){
            youTubePlayerView.initialize(ManageKeyVideo.API_KEY, PlayVideo.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
