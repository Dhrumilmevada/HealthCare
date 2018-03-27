package com.example.dhrumil.healthcare.yoga;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.common.Config;
import com.example.dhrumil.healthcare.common.YoutubeData;
import com.example.dhrumil.healthcare.common.YoutubePlayer;
import com.example.dhrumil.healthcare.common.YoutubeThumbnailView;
import com.example.dhrumil.healthcare.common.adapter.YoutubeVideoListAdapter;
import com.example.dhrumil.healthcare.common.listener.RecyclerViewOnClickListener;
import com.example.dhrumil.healthcare.common.model.YoutubeVideoListModel;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.diet.DietPlan;
import com.example.dhrumil.healthcare.fitness.FitnessRoutine;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.hospital.HospitalList;
import com.example.dhrumil.healthcare.laboratory.LaboratoryList;
import com.example.dhrumil.healthcare.login.LoginActivity;

import java.util.ArrayList;

public class YogaRoutine extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private String user_type;
    private DrawerLayout drawer_lay_yoga;
    private Toolbar tool_bar_yoga;
    private RecyclerView rec_view_youtube_yoga_thumbnail;
    private LinearLayoutManager linearLayoutManager;
    private YoutubeData youtubeData;
    private ArrayList<YoutubeVideoListModel> videoList;
    private YoutubeVideoListAdapter youtubeVideoListAdapter;
    private NavigationView nav_view_yoga;
    private ActionBarDrawerToggle drawerToggle;
    private Context mContext;
    private SharedPreference preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_routine);
        getIntentData();
        inti();
        register();
        setNavigationDrawer();
    }

    private void inti() {
        drawer_lay_yoga = (DrawerLayout) findViewById(R.id.drawer_lay_yoga);
        tool_bar_yoga = (Toolbar) findViewById(R.id.tool_bar_yoga);
        nav_view_yoga = (NavigationView) findViewById(R.id.nav_view_yoga);
        rec_view_youtube_yoga_thumbnail = (RecyclerView) findViewById(R.id.rec_view_youtube_yoga_thumbnail);
        tool_bar_yoga.setTitle(getResources().getString(R.string.yoga_meditation));
        setSupportActionBar(tool_bar_yoga);

        drawerToggle = new ActionBarDrawerToggle(this,drawer_lay_yoga,R.string.open,R.string.close);
        drawer_lay_yoga.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        youtubeData = new YoutubeData();
        videoList = new ArrayList<YoutubeVideoListModel>();
        getFilledYoutubeVideoList();
        youtubeVideoListAdapter  =new YoutubeVideoListAdapter(this,videoList);
    }

    private void register() {
        nav_view_yoga.setNavigationItemSelectedListener(this);
        rec_view_youtube_yoga_thumbnail.setLayoutManager(linearLayoutManager);
        rec_view_youtube_yoga_thumbnail.setAdapter(youtubeVideoListAdapter);

        rec_view_youtube_yoga_thumbnail.addOnItemTouchListener(new RecyclerViewOnClickListener(this, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(YogaRoutine.this,YoutubePlayer.class);
                i.putExtra(Config.VIDEO_ID,videoList.get(position).getVideoId());
                startActivity(i);
            }
        }));
    }

    private void getIntentData() {
        Intent i = getIntent();
        user_type = i.getStringExtra(LoginActivity.USER_TYPE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    private void setNavigationDrawer() {

        Menu menu;
        if (user_type.equals(getResources().getString(R.string.patient))) {
            nav_view_yoga.inflateMenu(R.menu.nav_menu_patient);
            nav_view_yoga.inflateHeaderView(R.layout.nav_header_view);
            menu = nav_view_yoga.getMenu();
            MenuItem menuItem = menu.getItem(7);
            menuItem.setChecked(true);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_yoga.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_yoga.inflateHeaderView(R.layout.nav_header_view);
            menu = nav_view_yoga.getMenu();
            MenuItem menuItem = menu.getItem(8);
            menuItem.setChecked(true);
        } else {
            nav_view_yoga.inflateMenu(R.menu.nav_menu_naive);
            nav_view_yoga.inflateHeaderView(R.layout.nav_header_naive);
            menu = nav_view_yoga.getMenu();
            MenuItem menuItem = menu.getItem(5);
            menuItem.setChecked(true);
        }
        //rel_lay_nav_header =  nav_view_home_page.getHeaderView(0);

    }

    @Override
    public void onBackPressed() {
        if(drawer_lay_yoga.isDrawerOpen(nav_view_yoga))
        {
            drawer_lay_yoga.closeDrawer(nav_view_yoga);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Menu menu;
        if (user_type.equals(getResources().getString(R.string.patient))) {
            menu = nav_view_yoga.getMenu();
            MenuItem menuItem = menu.getItem(7);
            menuItem.setChecked(true);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            menu = nav_view_yoga.getMenu();
            MenuItem menuItem = menu.getItem(9);
            menuItem.setChecked(true);
        } else {
            menu = nav_view_yoga.getMenu();
            MenuItem menuItem = menu.getItem(5);
            menuItem.setChecked(true);
        }

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_news_feed:
                item.setChecked(true);
                drawer_lay_yoga.closeDrawer(nav_view_yoga);
                Intent news = new Intent(YogaRoutine.this,HomePage.class);
                news.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(news);
                break;
            case R.id.nav_logout:
                item.setChecked(true);
                drawer_lay_yoga.closeDrawer(nav_view_yoga);
                preferences = new SharedPreference(this,SharedPreference.USER_INFO);
                preferences.clear();
                Intent  logout = new Intent(YogaRoutine.this,MainActivity.class);
                startActivity(logout);
                finish();
                break;
            case R.id.nav_hospital:
                item.setChecked(true);
                drawer_lay_yoga.closeDrawer(nav_view_yoga);
                Intent hospital = new Intent(YogaRoutine.this, HospitalList.class);
                hospital.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(hospital);
                break;
            case R.id.nav_laboratory:
                item.setChecked(true);
                drawer_lay_yoga.closeDrawer(nav_view_yoga);
                Intent laboratory = new Intent(YogaRoutine.this, LaboratoryList.class);
                laboratory.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(laboratory);
                break;
            case R.id.nav_diet:
                item.setChecked(true);
                drawer_lay_yoga.closeDrawer(nav_view_yoga);
                Intent diet = new Intent(YogaRoutine.this, DietPlan.class);
                diet.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(diet);
                break;
            case R.id.nav_workout:
                item.setChecked(true);
                drawer_lay_yoga.closeDrawer(nav_view_yoga);
                Intent workout = new Intent(YogaRoutine.this, FitnessRoutine.class);
                workout.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(workout);
                break;
            case R.id.nav_yago_meditation:
                item.setChecked(true);
                drawer_lay_yoga.closeDrawer(nav_view_yoga);
                Intent yoga = new Intent(YogaRoutine.this, YogaRoutine.class);
                yoga.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(yoga);
                finish();
                break;
        }
        return true;
    }

    private void getFilledYoutubeVideoList() {
        fetchYoutubeData(youtubeData.yoga_video_id,youtubeData.yoga_video_title,youtubeData.yoga_video_duration);

    }

    private void fetchYoutubeData(String [] video_id,String [] video_title,String [] video_duration)
    {
        for(int i =0; i < video_id.length ; i++) {
            YoutubeVideoListModel youtubeVideoListModel = new YoutubeVideoListModel(video_id[i],video_title[i],video_duration[i]);
            videoList.add(youtubeVideoListModel);
        }
    }
}
