package com.example.dhrumil.healthcare.hospital;

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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.diet.DietPlan;
import com.example.dhrumil.healthcare.fitness.FitnessRoutine;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.laboratory.LaboratoryList;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.yoga.YogaRoutine;

public class HospitalList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private String user_type;
    private DrawerLayout drawer_lay_hospital_list;
    private Toolbar tool_bar_hospital;
    private NavigationView nav_view_hospital;
    private ActionBarDrawerToggle drawerToggle;
    private Context mContext;
    private SharedPreference preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        inti();
        register();
        getIntentData();
        setNavigationDrawer();
    }

    private void inti() {

        drawer_lay_hospital_list = (DrawerLayout) findViewById(R.id.drawer_lay_hospital_list);
        tool_bar_hospital = (Toolbar) findViewById(R.id.tool_bar_hospital);
        nav_view_hospital = (NavigationView) findViewById(R.id.nav_view_hospital);
        tool_bar_hospital.setTitle(getResources().getString(R.string.hospital));
        setSupportActionBar(tool_bar_hospital);

        drawerToggle = new ActionBarDrawerToggle(this,drawer_lay_hospital_list,R.string.open,R.string.close);
        drawer_lay_hospital_list.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();
    }

    private void register()
    {
        nav_view_hospital.setNavigationItemSelectedListener(this);
    }

    private void getIntentData(){
        Intent i = getIntent();
        user_type = i.getStringExtra(LoginActivity.USER_TYPE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
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
            nav_view_hospital.inflateMenu(R.menu.nav_menu_patient);
            nav_view_hospital.inflateHeaderView(R.layout.nav_header_view);
            menu = nav_view_hospital.getMenu();
            MenuItem menuItem = menu.getItem(1);
            menuItem.setChecked(true);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_hospital.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_hospital.inflateHeaderView(R.layout.nav_header_view);
            menu = nav_view_hospital.getMenu();
            MenuItem menuItem = menu.getItem(2);
            menuItem.setChecked(true);
        } else {
            nav_view_hospital.inflateMenu(R.menu.nav_menu_naive);
            nav_view_hospital.inflateHeaderView(R.layout.nav_header_naive);
            menu = nav_view_hospital.getMenu();
            MenuItem menuItem = menu.getItem(1);
            menuItem.setChecked(true);
        }
        //rel_lay_nav_header =  nav_view_home_page.getHeaderView(0);

    }

    @Override
    public void onBackPressed() {
        if(drawer_lay_hospital_list.isDrawerOpen(nav_view_hospital))
        {
            drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
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
            menu = nav_view_hospital.getMenu();
            MenuItem menuItem = menu.getItem(1);
            menuItem.setChecked(true);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            menu = nav_view_hospital.getMenu();
            MenuItem menuItem = menu.getItem(2);
            menuItem.setChecked(true);
        } else {
            menu = nav_view_hospital.getMenu();
            MenuItem menuItem = menu.getItem(1);
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
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                Intent news = new Intent(HospitalList.this,HomePage.class);
                news.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(news);
                break;
            case R.id.nav_logout:
                item.setChecked(true);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                preferences = new SharedPreference(this,SharedPreference.USER_INFO);
                preferences.clear();
                Intent  logout = new Intent(HospitalList.this,MainActivity.class);
                startActivity(logout);
                finish();
                break;
            case R.id.nav_hospital:
                item.setChecked(true);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                Intent hospital = new Intent(HospitalList.this, HospitalList.class);
                hospital.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(hospital);
                finish();
                break;
            case R.id.nav_laboratory:
                item.setChecked(true);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                Intent laboratory = new Intent(HospitalList.this, LaboratoryList.class);
                laboratory.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(laboratory);
                break;
            case R.id.nav_diet:
                item.setChecked(true);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                Intent diet = new Intent(HospitalList.this, DietPlan.class);
                diet.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(diet);
                break;
            case R.id.nav_workout:
                item.setChecked(true);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                Intent workout = new Intent(HospitalList.this, FitnessRoutine.class);
                workout.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(workout);
                break;
            case R.id.nav_yago_meditation:
                item.setChecked(true);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                Intent yoga = new Intent(HospitalList.this, YogaRoutine.class);
                yoga.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(yoga);
                break;
        }
        return true;
    }
}
