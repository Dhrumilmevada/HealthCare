package com.example.dhrumil.healthcare.appointment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import com.example.dhrumil.healthcare.appointment.fragment.Confirm_Appointment;
import com.example.dhrumil.healthcare.appointment.fragment.Pending_Appointment;
import com.example.dhrumil.healthcare.casePaper.CasePaper;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.diet.DietPlan;
import com.example.dhrumil.healthcare.editProfile.EditProfile;
import com.example.dhrumil.healthcare.fitness.FitnessRoutine;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.hospital.HospitalList;
import com.example.dhrumil.healthcare.laboratory.LaboratoryList;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.resetPassword.ViewPagerAdapter;
import com.example.dhrumil.healthcare.yoga.YogaRoutine;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dhrumil.healthcare.R.id.float_btn_appointment;
import static com.example.dhrumil.healthcare.R.id.nav_view_appointment;

public class Appointment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private String user_type;
    private DrawerLayout drawer_lay_appointment;
    private Toolbar tool_bar_appointment;
    private NavigationView nav_view_appointment;
    private ActionBarDrawerToggle drawerToggle;
    private Context mContext;
    private AppBarLayout appbar_lay_appointment;
    private TabLayout tab_lay_appontment;
    private ViewPager view_pager_appointment;
    private ViewPagerAdapter viewPagerAdapter;
    private View rel_lay_nav_header;
    private CircleImageView circle_image_profile_nav_header;
    private TextView txt_name_nav_header;
    private TextView txt_email_nav_header;
    private TextView txt_edit_profile_nav_header;
    private SharedPreference preferences;
    private FloatingActionButton float_btn_appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        getIntentData();
        inti();
        register();
        setNavigationDrawer();
    }

    private void inti() {
        drawer_lay_appointment = (DrawerLayout) findViewById(R.id.drawer_lay_appointment);
        tool_bar_appointment = (Toolbar) findViewById(R.id.tool_bar_appointment);
        nav_view_appointment = (NavigationView)findViewById(R.id.nav_view_appointment);
        tool_bar_appointment.setTitle(getResources().getString(R.string.appointment));
        setSupportActionBar(tool_bar_appointment);
        
        drawerToggle = new ActionBarDrawerToggle(this,drawer_lay_appointment,R.string.open,R.string.close);
        drawer_lay_appointment.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();

        appbar_lay_appointment = (AppBarLayout) findViewById(R.id.appbar_lay_appointment);
        tab_lay_appontment = (TabLayout) findViewById(R.id.tab_lay_appointment);
        view_pager_appointment = (ViewPager)findViewById(R.id.view_pager_appointment);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        float_btn_appointment = findViewById(R.id.float_btn_appointment);
    }

    private void register() {
        nav_view_appointment.setNavigationItemSelectedListener(this);
        viewPagerAdapter.addToViewPager(new Confirm_Appointment(),getResources().getString(R.string.confirm));
        viewPagerAdapter.addToViewPager(new Pending_Appointment(),getResources().getString(R.string.pending));
        view_pager_appointment.setAdapter(viewPagerAdapter);
        tab_lay_appontment.setupWithViewPager(view_pager_appointment);
        float_btn_appointment.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent i = getIntent();
        user_type = i.getStringExtra(LoginActivity.USER_TYPE);
    }

    public Bundle getUserType(){
        Bundle data = new Bundle();
        data.putString(LoginActivity.USER_TYPE,user_type);
        return  data;
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
            nav_view_appointment.inflateMenu(R.menu.nav_menu_patient);
            nav_view_appointment.inflateHeaderView(R.layout.nav_header_view);

            menu = nav_view_appointment.getMenu();
            MenuItem menuItem = menu.getItem(3);
            menuItem.setChecked(true);


            rel_lay_nav_header =  nav_view_appointment.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_appointment.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_appointment.inflateHeaderView(R.layout.nav_header_view);

            menu = nav_view_appointment.getMenu();
            MenuItem menuItem = menu.getItem(4);
            menuItem.setChecked(true);


            rel_lay_nav_header =  nav_view_appointment.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer_lay_appointment.isDrawerOpen(nav_view_appointment))
        {
            drawer_lay_appointment.closeDrawer(nav_view_appointment);
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
            menu = nav_view_appointment.getMenu();
            MenuItem menuItem = menu.getItem(3);
            menuItem.setChecked(true);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            menu = nav_view_appointment.getMenu();
            MenuItem menuItem = menu.getItem(4);
            menuItem.setChecked(true);
        } 
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_news_feed:
                item.setChecked(true);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                Intent news = new Intent(Appointment.this,HomePage.class);
                news.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(news);
                break;
            case R.id.nav_book_appointment:
                item.setChecked(true);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                Intent book = new Intent(Appointment.this,Appointment.class);
                book.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(book);
                finish();
                break;
            case R.id.nav_logout:
                item.setChecked(true);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                preferences = new SharedPreference(this,SharedPreference.USER_INFO);
                preferences.clear();
                Intent  logout = new Intent(Appointment.this,MainActivity.class);
                startActivity(logout);
                finish();
                break;
            case R.id.nav_hospital:
                item.setChecked(true);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                Intent hospital = new Intent(Appointment.this, HospitalList.class);
                hospital.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(hospital);
                break;
            case R.id.nav_laboratory:
                item.setChecked(true);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                Intent laboratory = new Intent(Appointment.this, LaboratoryList.class);
                laboratory.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(laboratory);
                break;
            case R.id.nav_diet:
                item.setChecked(true);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                Intent diet = new Intent(Appointment.this, DietPlan.class);
                diet.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(diet);
                break;
            case R.id.nav_workout:
                item.setChecked(true);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                Intent workout = new Intent(Appointment.this, FitnessRoutine.class);
                workout.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(workout);
                break;
            case R.id.nav_yago_meditation:
                item.setChecked(true);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                Intent yoga = new Intent(Appointment.this, YogaRoutine.class);
                yoga.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(yoga);
                break;
            case R.id.nav_case_paper:
                item.setChecked(true);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                Intent paper = new Intent(Appointment.this,CasePaper.class);
                paper.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(paper);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_edit_profile_nav_header:
                Intent edit = new Intent(Appointment.this, EditProfile.class);
                edit.putExtra(LoginActivity.USER_TYPE,user_type);
                drawer_lay_appointment.closeDrawer(nav_view_appointment);
                startActivity(edit);
                break;
            case R.id.float_btn_appointment:
                Intent float_button = new Intent(Appointment.this,New_Appointment.class);
                startActivity(float_button);
                break;
        }
    }
}
