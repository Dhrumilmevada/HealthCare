package com.example.dhrumil.healthcare.homePage;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.login.LoginActivity;
import me.relex.circleindicator.CircleIndicator;

public class HomePage extends AppCompatActivity {

    private String user_type;
    private DrawerLayout drawer_lay_home_page;
    private NavigationView nav_view_home_page;
    private Toolbar tool_bar_home;
    private ViewPager view_pager_image_home_page;
    private CircleIndicator circle_indicator_home_page;
    private SliderViewPagerAdapter adapter;
    private int image[] = {R.drawable.hospital,R.drawable.hospital,R.drawable.hospital,R.drawable.hospital};
    private View rel_lay_nav_header;
    private ActionBarDrawerToggle drawerToggle;
    //private Handler handler;
    //private Timer swipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        inti();
        getIntentData();
        setNavigationDrawer();
       // setSlidePageListner();
        //setTimerSlider();

    }

    @Override
    public void onBackPressed() {
        if(drawer_lay_home_page.isDrawerOpen(nav_view_home_page))
        {
            drawer_lay_home_page.closeDrawer(nav_view_home_page);
        }
        else {
            super.onBackPressed();
        }
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

    private void inti() {
        drawer_lay_home_page = (DrawerLayout) findViewById(R.id.drawer_lay_home_page);
        nav_view_home_page = (NavigationView) findViewById(R.id.nav_view_home_page);
        tool_bar_home = (Toolbar) findViewById(R.id.tool_bar_home);
        tool_bar_home.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(tool_bar_home);

        view_pager_image_home_page = (ViewPager) findViewById(R.id.view_pager_image_home_page);
        circle_indicator_home_page = (CircleIndicator) findViewById(R.id.circle_indicator_home_page);
        adapter = new SliderViewPagerAdapter(this,image);
        view_pager_image_home_page.setAdapter(adapter);
        circle_indicator_home_page.setViewPager(view_pager_image_home_page);

        drawerToggle = new ActionBarDrawerToggle(this,drawer_lay_home_page,R.string.open,R.string.close);
        drawer_lay_home_page.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // handler = new Handler();
        //swipe = new Timer();



    }

    private void getIntentData(){
        Intent i = getIntent();
        user_type = i.getStringExtra(LoginActivity.USER_TYPE);
    }

    private void setNavigationDrawer(){
        if(user_type.equals(getResources().getString(R.string.patient))){
            nav_view_home_page.inflateMenu(R.menu.nav_menu_patient);
            nav_view_home_page.inflateHeaderView(R.layout.nav_header_view);

        }
        else if (user_type.equals(getResources().getString(R.string.doctor))){
            nav_view_home_page.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_home_page.inflateHeaderView(R.layout.nav_header_view);
        }
        else{
            nav_view_home_page.inflateMenu(R.menu.nav_menu_naive);
            nav_view_home_page.inflateHeaderView(R.layout.nav_header_naive);
        }
        //rel_lay_nav_header =  nav_view_home_page.getHeaderView(0);
    }


    /*private void setSlidePageListner() {
        view_pager_image_home_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }*/

    /*private void setTimerSlider(){
        swipe.schedule(new TimerTask() {
            @Override
            public void run() {
                  handler.post(new Runnable() {
                      @Override
                      public void run() {
                          int currenPage = view_pager_image_home_page.getCurrentItem();
                          if(currenPage == image.length)
                          {
                              currenPage = 0;
                          }
                          view_pager_image_home_page.setCurrentItem(currenPage++,true);

                      }
                  });
            }
        },1000,1000);
    }*/
}
