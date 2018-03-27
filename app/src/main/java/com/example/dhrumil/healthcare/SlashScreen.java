package com.example.dhrumil.healthcare;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.login.LoginActivity;



public class SlashScreen extends AppCompatActivity {
    private SharedPreference preferences;
    private Handler handler;
    private ImageView img_view_logo;
    private Animation slash_animation;
    private String username;
    private String usertype;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);
        inti();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                preferences = new SharedPreference(getBaseContext(),SharedPreference.USER_INFO);
                username  = preferences.getSharedPreference(SharedPreference.USER_NAME);
               // usertype  = preferences.getSharedPreference(SharedPreference.USER_TYPE);
                usertype = db.getUsertype();
                intiSharedPreference(username ,usertype);
            }
        }, 3000);
    }

    private void inti() {

        handler = new Handler();
        img_view_logo = (ImageView) findViewById(R.id.img_view_logo);
        slash_animation = AnimationUtils.loadAnimation(this,R.anim.slash_animation);
        img_view_logo.startAnimation(slash_animation);
        db = new Database(this);
    }

    private void intiSharedPreference(String username , String usertype){

        if(username.isEmpty()){
            Intent i = new Intent(SlashScreen.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else{
            Intent i = new Intent(SlashScreen.this,HomePage.class);
            i.putExtra(LoginActivity.USER_TYPE,usertype);
            startActivity(i);
            finish();
        }
    }
}
