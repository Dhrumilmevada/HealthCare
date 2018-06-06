package com.example.dhrumil.healthcare.laboratory;

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
import android.view.View;
import android.widget.TextView;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.appointment.Appointment;
import com.example.dhrumil.healthcare.casePaper.CasePaper;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.diet.DietPlan;
import com.example.dhrumil.healthcare.editProfile.EditProfile;
import com.example.dhrumil.healthcare.fitness.FitnessRoutine;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.hospital.HospitalList;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.yoga.YogaRoutine;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dhrumil.healthcare.R.id.nav_view_appointment;

public class LaboratoryList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private String user_type;
    private DrawerLayout drawer_lay_laboratory_list;
    private Toolbar tool_bar_laboratory;
    private NavigationView nav_view_laboratory;
    private ActionBarDrawerToggle drawerToggle;
    private Context mContext;
    private SharedPreference preferences;
    private View rel_lay_nav_header;
    private CircleImageView circle_image_profile_nav_header;
    private TextView txt_name_nav_header;
    private TextView txt_email_nav_header;
    private TextView txt_edit_profile_nav_header;

    private TextView txt_signup_nav_header;
    private TextView txt_login_nav_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory_list);
        getIntentData();
        inti();
        register();
        setNavigationDrawer();
    }

    private void inti() {
        drawer_lay_laboratory_list = (DrawerLayout) findViewById(R.id.drawer_lay_laboratory_list);
        tool_bar_laboratory = (Toolbar) findViewById(R.id.tool_bar_laboratory);
        nav_view_laboratory = (NavigationView) findViewById(R.id.nav_view_laboratory);
        tool_bar_laboratory.setTitle(getResources().getString(R.string.laboratory));
        setSupportActionBar(tool_bar_laboratory);

        drawerToggle = new ActionBarDrawerToggle(this,drawer_lay_laboratory_list,R.string.open,R.string.close);
        drawer_lay_laboratory_list.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();
    }

    private void register() {
        nav_view_laboratory.setNavigationItemSelectedListener(this);
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
            nav_view_laboratory.inflateMenu(R.menu.nav_menu_patient);
            nav_view_laboratory.inflateHeaderView(R.layout.nav_header_view);

            menu = nav_view_laboratory.getMenu();
            MenuItem menuItem = menu.getItem(2);
            menuItem.setChecked(true);


            rel_lay_nav_header =  nav_view_laboratory.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_laboratory.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_laboratory.inflateHeaderView(R.layout.nav_header_view);

            menu = nav_view_laboratory.getMenu();
            MenuItem menuItem = menu.getItem(3);
            menuItem.setChecked(true);


            rel_lay_nav_header =  nav_view_laboratory.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);
        } else {
            nav_view_laboratory.inflateMenu(R.menu.nav_menu_naive);
            nav_view_laboratory.inflateHeaderView(R.layout.nav_header_naive);
            menu = nav_view_laboratory.getMenu();
            MenuItem menuItem = menu.getItem(2);
            menuItem.setChecked(true);
            rel_lay_nav_header = nav_view_laboratory.getHeaderView(0);
            txt_signup_nav_header = rel_lay_nav_header.findViewById(R.id.txt_signup_nav_header);
            txt_login_nav_header = rel_lay_nav_header.findViewById(R.id.txt_login_nav_header);
            txt_login_nav_header.setOnClickListener(this);
            txt_signup_nav_header.setOnClickListener(this);
        }

    }

    @Override
    public void onBackPressed() {
        if(drawer_lay_laboratory_list.isDrawerOpen(nav_view_laboratory))
        {
            drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
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
            menu = nav_view_laboratory.getMenu();
            MenuItem menuItem = menu.getItem(2);
            menuItem.setChecked(true);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            menu = nav_view_laboratory.getMenu();
            MenuItem menuItem = menu.getItem(3);
            menuItem.setChecked(true);
        } else {
            menu = nav_view_laboratory.getMenu();
            MenuItem menuItem = menu.getItem(2);
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
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                Intent news = new Intent(LaboratoryList.this,HomePage.class);
                news.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(news);
                break;
            case R.id.nav_logout:
                item.setChecked(true);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                preferences = new SharedPreference(this,SharedPreference.USER_INFO);
                preferences.clear();
                Intent  logout = new Intent(LaboratoryList.this,MainActivity.class);
                startActivity(logout);
                finish();
                break;
            case R.id.nav_hospital:
                item.setChecked(true);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                Intent hospital = new Intent(LaboratoryList.this, HospitalList.class);
                hospital.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(hospital);
                break;
            case R.id.nav_laboratory:
                item.setChecked(true);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                Intent laboratory = new Intent(LaboratoryList.this, LaboratoryList.class);
                laboratory.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(laboratory);
                finish();
                break;
            case R.id.nav_diet:
                item.setChecked(true);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                Intent diet = new Intent(LaboratoryList.this, DietPlan.class);
                diet.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(diet);
                break;
            case R.id.nav_workout:
                item.setChecked(true);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                Intent workout = new Intent(LaboratoryList.this, FitnessRoutine.class);
                workout.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(workout);
                break;
            case R.id.nav_yago_meditation:
                item.setChecked(true);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                Intent yoga = new Intent(LaboratoryList.this, YogaRoutine.class);
                yoga.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(yoga);
                break;
            case R.id.nav_book_appointment:
                item.setChecked(true);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                Intent book = new Intent(LaboratoryList.this,Appointment.class);
                book.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(book);
                break;
            case R.id.nav_case_paper:
                item.setChecked(true);
                drawer_lay_laboratory_list.closeDrawer(nav_view_appointment);
                Intent paper = new Intent(LaboratoryList.this,CasePaper.class);
                paper.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(paper);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_login_nav_header:
                Intent main = new Intent(LaboratoryList.this,MainActivity.class);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                startActivity(main);
                break;
            case R.id.txt_signup_nav_header:
                Intent signup = new Intent(LaboratoryList.this,LoginActivity.class);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                startActivity(signup);
                break;
            case R.id.txt_edit_profile_nav_header:
                Intent edit = new Intent(LaboratoryList.this, EditProfile.class);
                edit.putExtra(LoginActivity.USER_TYPE,user_type);
                drawer_lay_laboratory_list.closeDrawer(nav_view_laboratory);
                startActivity(edit);
                break;
        }
    }
}

