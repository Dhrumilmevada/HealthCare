package com.example.dhrumil.healthcare.resetPassword;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.resetPassword.fragment.EmailFragment;
import com.example.dhrumil.healthcare.resetPassword.fragment.PhoneNoFragment;

public class ResetPassword extends AppCompatActivity implements OnClickInFragment {

    private Toolbar toolbar_reset_password;
    private AppBarLayout appbar_lay_reset_password;
    private TabLayout tab_lay_reset_password;
    private ViewPager view_pager_reset_password;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        inti();
        register();
    }


     private void register() {
        viewPagerAdapter.addToViewPager(new EmailFragment(), getResources().getString(R.string.email_id));
        viewPagerAdapter.addToViewPager(new PhoneNoFragment(), getResources().getString(R.string.phone_no));
        view_pager_reset_password.setAdapter(viewPagerAdapter);
        tab_lay_reset_password.setupWithViewPager(view_pager_reset_password);
        toolbar_reset_password.setTitle(getResources().getString(R.string.reset_password));
        setSupportActionBar(toolbar_reset_password);
    }

    private void inti() {
        toolbar_reset_password = (Toolbar) findViewById(R.id.toolbar_reset_password);
        appbar_lay_reset_password = (AppBarLayout) findViewById(R.id.appbar_lay_reset_password);
        tab_lay_reset_password = (TabLayout) findViewById(R.id.tab_lay_reset_password);
        view_pager_reset_password= (ViewPager) findViewById(R.id.view_pager_reset_password);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager()/*,view_pager_reset_password*/);

    }
    @Override
    public void onClickButton() {
        Intent i =new Intent(ResetPassword.this,ResetPasswordContinue.class);
        startActivity(i);
    }
    @Override
    public void onClickButton(Class cls) {

    }
}
