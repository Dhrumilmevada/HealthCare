package com.example.dhrumil.healthcare.common;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.common.adapter.YoutubeVideoListAdapter;
import com.example.dhrumil.healthcare.common.listener.RecyclerViewOnClickListener;
import com.example.dhrumil.healthcare.common.model.YoutubeVideoListModel;
import com.example.dhrumil.healthcare.login.LoginActivity;

import java.util.ArrayList;

public class YoutubeThumbnailView extends AppCompatActivity {

    private Toolbar tool_bar_youtube_thumbnail;
    private RecyclerView rec_view_youtube_thumbnail;
    private LinearLayoutManager linearLayoutManager;
    private YoutubeVideoListAdapter youtubeVideoListAdapter;
    private String diet_title;
    private String fitness_title;
    private YoutubeData youtubeData;
    private ArrayList<YoutubeVideoListModel> videoList;
    private String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_thumbnail_view);
        getIntentData();
        inti();
        register();
    }

    private void inti() {
        tool_bar_youtube_thumbnail = (Toolbar)findViewById(R.id.tool_bar_youtube_thumbnail);
        rec_view_youtube_thumbnail = (RecyclerView) findViewById(R.id.rec_view_youtube_thumbnail);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        youtubeData = new YoutubeData();
        videoList = new ArrayList<YoutubeVideoListModel>();
        getFilledYoutubeVideoList();
        youtubeVideoListAdapter  =new YoutubeVideoListAdapter(this,videoList);
    }

    private void getIntentData()
    {
        Intent i = getIntent();
        diet_title = i.getStringExtra(Config.DIET_TITLE);
        fitness_title = i.getStringExtra(Config.FITNESS_TITLE);


    }

    private void register()
    {
        if(diet_title != null)
            tool_bar_youtube_thumbnail.setTitle(diet_title);
        else
            tool_bar_youtube_thumbnail.setTitle(fitness_title);

        setSupportActionBar(tool_bar_youtube_thumbnail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rec_view_youtube_thumbnail.setLayoutManager(linearLayoutManager);
        rec_view_youtube_thumbnail.setAdapter(youtubeVideoListAdapter);

        rec_view_youtube_thumbnail.addOnItemTouchListener(new RecyclerViewOnClickListener(this, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(YoutubeThumbnailView.this,YoutubePlayer.class);
                i.putExtra(Config.VIDEO_ID,videoList.get(position).getVideoId());
                startActivity(i);
            }
        }));
    }

    private void getFilledYoutubeVideoList() {
        if(diet_title != null)
        {
            if(diet_title.equals(youtubeData.diet_title[0])) {
                fetchYoutubeData(youtubeData.high_bp_video_id,youtubeData.high_bp_title,youtubeData.high_bp_duration);
            }
            else if(diet_title.equals(youtubeData.diet_title[1])) {
                fetchYoutubeData(youtubeData.control_diebetes_video_id,youtubeData.control_diebetes_title,youtubeData.control_diebetes_duration);
            }
            else if (diet_title.equals(youtubeData.diet_title[2])) {
                fetchYoutubeData(youtubeData.control_cholesterol_video_id,youtubeData.control_cholesterol_title,youtubeData.control_cholesterol_duration);
            }
            else if(diet_title.equals(youtubeData.diet_title[3])){
                fetchYoutubeData(youtubeData.diet_40_video_id,youtubeData.diet_40_title,youtubeData.diet_40_duration);
            }
            else if (diet_title.equals(youtubeData.diet_title[4])){
                fetchYoutubeData(youtubeData.drug_rehab_video_id,youtubeData.drug_rehab_title,youtubeData.drug_rehab_duration);
            }
            else if (diet_title.equals(youtubeData.diet_title[5])){
                fetchYoutubeData(youtubeData.fight_cancer_video_id,youtubeData.fight_cancer_title,youtubeData.fight_cancer_duration);
            }
            else if (diet_title.equals(youtubeData.diet_title[6])){
                fetchYoutubeData(youtubeData.fat_loss_video_id,youtubeData.fat_loss_video_title,youtubeData.fat_loss_video_duration);
            }
            else if (diet_title.equals(youtubeData.diet_title[7])){
                fetchYoutubeData(youtubeData.muscle_building_video_id,youtubeData.muscle_building_title,youtubeData.muscle_building_duration);
            }
        }
        else if(fitness_title != null){
            if(fitness_title.equals(youtubeData.fitness_title[0])){
                fetchYoutubeData(youtubeData.health_video_id,youtubeData.health_video_title,youtubeData.health_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[1])){
                fetchYoutubeData(youtubeData.forArm_video_id,youtubeData.forArm_video_title,youtubeData.forArm_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[2])){
                fetchYoutubeData(youtubeData.biceps_video_id,youtubeData.biceps_video_title,youtubeData.biceps_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[3])){
                fetchYoutubeData(youtubeData.tricep_video_id,youtubeData.tricep_video_title,youtubeData.tricep_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[4])){
                fetchYoutubeData(youtubeData.chest_video_id,youtubeData.chest_video_title,youtubeData.chest_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[5])){
                fetchYoutubeData(youtubeData.shoulder_video_id,youtubeData.shoulder_video_title,youtubeData.shoulder_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[6])){
                fetchYoutubeData(youtubeData.back_video_id,youtubeData.back_video_title,youtubeData.back_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[7])){
                fetchYoutubeData(youtubeData.abs_video_id,youtubeData.abs_video_title,youtubeData.abs_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[8])){
                fetchYoutubeData(youtubeData.leg_video_id,youtubeData.leg_video_title,youtubeData.leg_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[9])){
                fetchYoutubeData(youtubeData.home_video_id,youtubeData.home_video_title,youtubeData.home_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[10])){
                fetchYoutubeData(youtubeData.cal_video_id,youtubeData.cal_video_title,youtubeData.cal_video_duration);
            }
            else if(fitness_title.equals(youtubeData.fitness_title[11])) {
                fetchYoutubeData(youtubeData.warm_video_id, youtubeData.warm_video_title, youtubeData.warm_video_duration);
            }
        }
    }

    private void fetchYoutubeData(String [] video_id,String [] video_title,String [] video_duration)
    {
        for(int i =0; i < video_id.length ; i++) {
            YoutubeVideoListModel youtubeVideoListModel = new YoutubeVideoListModel(video_id[i],video_title[i],video_duration[i]);
            videoList.add(youtubeVideoListModel);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
