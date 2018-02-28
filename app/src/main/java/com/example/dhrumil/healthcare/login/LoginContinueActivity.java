package com.example.dhrumil.healthcare.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.login.Frag.DoctorLoginFragment;
import com.example.dhrumil.healthcare.login.Frag.PatientLoginFragment;
import com.example.dhrumil.healthcare.resetPassword.OnClickInFragment;

/**
 * Created by Dhrumil on 2/19/2018.
 */
public class LoginContinueActivity extends AppCompatActivity implements OnClickInFragment{

    private FrameLayout frame_lay_login_continue;
    private String userType;
    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_continue);
        inti();
        register();
        getIntentData();
        setFragment();
    }

    private void inti() {
        frame_lay_login_continue = (FrameLayout) findViewById(R.id.frame_lay_login_continue);

    }
    private void register(){

    }


    private void setFragment() {
        fm = getSupportFragmentManager();
        Fragment frag;
        if(userType.equals(getResources().getString(R.string.patient)))
        {
            frag = new PatientLoginFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_lay_login_continue,frag).commit();
        }
        else if(userType.equals(getResources().getString(R.string.doctor)))
        {
            frag = new DoctorLoginFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_lay_login_continue,frag).commit();
        }
    }

    public void getIntentData() {
        Intent i = getIntent();
        userType = i.getStringExtra(LoginActivity.USER_TYPE);
    }
    @Override
    public void onClickButton() {

    }

    @Override
    public void onClickButton(Class cls) {
        Intent i = new Intent(LoginContinueActivity.this,cls);
        i.putExtra(LoginActivity.USER_TYPE,userType);
        startActivity(i);
        finish();
    }
}
