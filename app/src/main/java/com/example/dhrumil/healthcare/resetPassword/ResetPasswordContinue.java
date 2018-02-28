package com.example.dhrumil.healthcare.resetPassword;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.resetPassword.fragment.EnterOtpFragment;
import com.example.dhrumil.healthcare.resetPassword.fragment.SetNewPasswordFragment;

public class ResetPasswordContinue extends AppCompatActivity implements OnClickInFragment{

    private Toolbar toolbar_reset_password_cont;
    private FrameLayout frame_reset_password_Continue;
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_continue);
        inti();
        register();
        setFragment();
    }

    private void inti() {
        frame_reset_password_Continue = (FrameLayout) findViewById(R.id.frame_reset_password_continue);
        toolbar_reset_password_cont = (Toolbar) findViewById(R.id.toolbar_reset_password_cont);
    }

    private void register() {
        toolbar_reset_password_cont.setTitle(getResources().getString(R.string.reset_password));
        setSupportActionBar(toolbar_reset_password_cont);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*toolbar_reset_password_cont.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/
    }


    private void setFragment() {
        Fragment frag = new EnterOtpFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_reset_password_continue,frag,"EnterOptFragment").commit();
    }

    private void setFragment(Fragment frag){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_reset_password_continue,frag).commit();
    }

    @Override
    public void onClickButton() {
        setFragment(new SetNewPasswordFragment());
    }

    @Override
    public void onClickButton(Class cls) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
