package com.example.dhrumil.healthcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.login.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

public class SlashScreen extends AppCompatActivity {
    private SharedPreferences preferences;
    private Handler handler;
    private ImageView img_view_logo;
    private Animation slash_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);
        inti();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intiSharedPreference();
            }
        }, 3000);
    }

    private void inti() {
        handler = new Handler();
        img_view_logo = (ImageView) findViewById(R.id.img_view_logo);
        slash_animation = AnimationUtils.loadAnimation(this,R.anim.slash_animation);
        img_view_logo.startAnimation(slash_animation);
    }

    private void intiSharedPreference(){
        preferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
        String userName = preferences.getString("USER_NAME","");
        String userType = preferences.getString("USER_TYPE","");

        if(userName.isEmpty()){
            Intent i = new Intent(SlashScreen.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else{
            Intent i = new Intent(SlashScreen.this,HomePage.class);
            i.putExtra(LoginActivity.USER_TYPE,userType);
            startActivity(i);
            finish();
        }
    }
}
