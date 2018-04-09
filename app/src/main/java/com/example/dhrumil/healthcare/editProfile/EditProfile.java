package com.example.dhrumil.healthcare.editProfile;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.editProfile.fragment.EditDoctorProfileFragment;
import com.example.dhrumil.healthcare.editProfile.fragment.EditPatientProfileFragment;
import com.example.dhrumil.healthcare.login.LoginActivity;

public class EditProfile extends AppCompatActivity {

    private Toolbar tool_bar_edit_profile;
    private FrameLayout frame_lay_edit_profile;
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getIntentData();
        inti();
        register();
    }

    private void getIntentData() {
        Intent i = getIntent();
        userType = i.getStringExtra(LoginActivity.USER_TYPE);
    }

    private void inti() {
        tool_bar_edit_profile = (Toolbar)findViewById(R.id.tool_bar_edit_profile);
        frame_lay_edit_profile = (FrameLayout)findViewById(R.id.frame_lay_edit_profile);
        tool_bar_edit_profile.setTitle(getResources().getString(R.string.edit_profile));
        setSupportActionBar(tool_bar_edit_profile);
    }

    private void register() {
        if(userType.equals(getResources().getString(R.string.patient))) {
            fManager = getSupportFragmentManager();
            fTransaction = fManager.beginTransaction();
            Fragment frag = new EditPatientProfileFragment();
            fTransaction.replace(R.id.frame_lay_edit_profile, frag).commit();
        }
        else if(userType.equals(getResources().getString(R.string.doctor))) {
            fManager = getSupportFragmentManager();
            fTransaction = fManager.beginTransaction();
            Fragment frag = new EditDoctorProfileFragment();
            fTransaction.replace(R.id.frame_lay_edit_profile, frag).commit();
        }
    }
}
