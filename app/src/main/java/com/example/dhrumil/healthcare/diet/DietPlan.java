package com.example.dhrumil.healthcare.diet;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.appointment.Appointment;
import com.example.dhrumil.healthcare.casePaper.CasePaper;
import com.example.dhrumil.healthcare.common.YoutubeData;
import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.diet.adapter.YoutubeCatagoryAdapter;
import com.example.dhrumil.healthcare.diet.model.YoutubeCatagoryModel;
import com.example.dhrumil.healthcare.editProfile.EditProfile;
import com.example.dhrumil.healthcare.fitness.FitnessRoutine;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.hospital.HospitalList;
import com.example.dhrumil.healthcare.laboratory.LaboratoryList;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.yoga.YogaRoutine;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dhrumil.healthcare.R.id.nav_view_appointment;

public class DietPlan extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private String user_type;
    private DrawerLayout drawer_lay_diet;
    private Toolbar tool_bar_diet;
    private RecyclerView rec_view_diet;
    private NavigationView nav_view_diet;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<YoutubeCatagoryModel> catagoryList;
    private YoutubeCatagoryAdapter youtubeCatagoryAdapter;
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
    private Database db;
    private YoutubeData youtubeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan);
        getIntentData();
        inti();
        register();
        setNavigationDrawer();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void inti() {
        drawer_lay_diet = (DrawerLayout) findViewById(R.id.drawer_lay_diet);
        tool_bar_diet = (Toolbar) findViewById(R.id.tool_bar_diet);
        nav_view_diet = (NavigationView) findViewById(R.id.nav_view_diet);
        tool_bar_diet.setTitle( getResources().getString(R.string.diet));
        setSupportActionBar(tool_bar_diet);

        drawerToggle = new ActionBarDrawerToggle(this,drawer_lay_diet,R.string.open,R.string.close);
        drawer_lay_diet.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();
        rec_view_diet = (RecyclerView) findViewById(R.id.rec_view_diet);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        youtubeData = new YoutubeData();
        catagoryList = new ArrayList<YoutubeCatagoryModel>();
        getFilledCatagoryList();
        youtubeCatagoryAdapter = new YoutubeCatagoryAdapter(this,catagoryList);
        db = new Database(this);

    }

    private void register() {
        nav_view_diet.setNavigationItemSelectedListener(this);
        rec_view_diet.setLayoutManager(linearLayoutManager);
        rec_view_diet.setAdapter(youtubeCatagoryAdapter);
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
            nav_view_diet.inflateMenu(R.menu.nav_menu_patient);
            nav_view_diet.inflateHeaderView(R.layout.nav_header_view);
            menu = nav_view_diet.getMenu();
            MenuItem menuItem = menu.getItem(5);
            menuItem.setChecked(true);

            rel_lay_nav_header =  nav_view_diet.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_diet.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_diet.inflateHeaderView(R.layout.nav_header_view);
            menu = nav_view_diet.getMenu();
            MenuItem menuItem = menu.getItem(6);
            menuItem.setChecked(true);

            rel_lay_nav_header =  nav_view_diet.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);
        }
        else {
            nav_view_diet.inflateMenu(R.menu.nav_menu_naive);
            nav_view_diet.inflateHeaderView(R.layout.nav_header_naive);
            menu = nav_view_diet.getMenu();
            MenuItem menuItem = menu.getItem(3);
            menuItem.setChecked(true);
            rel_lay_nav_header = nav_view_diet.getHeaderView(0);
            txt_signup_nav_header = rel_lay_nav_header.findViewById(R.id.txt_signup_nav_header);
            txt_login_nav_header = rel_lay_nav_header.findViewById(R.id.txt_login_nav_header);
            txt_login_nav_header.setOnClickListener(this);
            txt_signup_nav_header.setOnClickListener(this);
        }


    }

    @Override
    public void onBackPressed() {
        if(drawer_lay_diet.isDrawerOpen(nav_view_diet))
        {
            drawer_lay_diet.closeDrawer(nav_view_diet);
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
            menu = nav_view_diet.getMenu();
            MenuItem menuItem = menu.getItem(5);
            menuItem.setChecked(true);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            menu = nav_view_diet.getMenu();
            MenuItem menuItem = menu.getItem(7);
            menuItem.setChecked(true);
        } else {
            menu = nav_view_diet.getMenu();
            MenuItem menuItem = menu.getItem(3);
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
                drawer_lay_diet.closeDrawer(nav_view_diet);
                Intent news = new Intent(DietPlan.this,HomePage.class);
                news.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(news);
                break;
            case R.id.nav_logout:
                item.setChecked(true);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                preferences = new SharedPreference(this,SharedPreference.USER_INFO);
                preferences.clear();
                Intent  logout = new Intent(DietPlan.this,MainActivity.class);
                startActivity(logout);
                finish();
                break;
            case R.id.nav_hospital:
                item.setChecked(true);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                Intent hospital = new Intent(DietPlan.this, HospitalList.class);
                hospital.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(hospital);
                break;
            case R.id.nav_laboratory:
                item.setChecked(true);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                Intent laboratory = new Intent(DietPlan.this, LaboratoryList.class);
                laboratory.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(laboratory);
                break;
            case R.id.nav_diet:
                item.setChecked(true);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                Intent diet = new Intent(DietPlan.this, DietPlan.class);
                diet.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(diet);
                finish();
                break;
            case R.id.nav_workout:
                item.setChecked(true);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                Intent workout = new Intent(DietPlan.this, FitnessRoutine.class);
                workout.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(workout);
                break;
            case R.id.nav_yago_meditation:
                item.setChecked(true);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                Intent yoga = new Intent(DietPlan.this, YogaRoutine.class);
                yoga.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(yoga);
                break;
            case R.id.nav_book_appointment:
                item.setChecked(true);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                Intent book = new Intent(DietPlan.this,Appointment.class);
                book.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(book);
                break;
            case R.id.nav_case_paper:
                item.setChecked(true);
                drawer_lay_diet.closeDrawer(nav_view_appointment);
                Intent paper = new Intent(DietPlan.this,CasePaper.class);
                paper.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(paper);
                break;
        }
        return true;
    }

    private void getFilledCatagoryList() {
        String[] title = youtubeData.diet_title;
        String[] desc = youtubeData.diet_desc;
        int [] img = youtubeData.diet_image;

        for (int i= 0 ; i< title.length ; i++)
        {
            YoutubeCatagoryModel youtubeCatagoryModel = new YoutubeCatagoryModel(title[i],desc[i],img[i]);
            catagoryList.add(youtubeCatagoryModel);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_login_nav_header:
                Intent main = new Intent(DietPlan.this,MainActivity.class);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                startActivity(main);
                break;
            case R.id.txt_signup_nav_header:
                Intent signup = new Intent(DietPlan.this,LoginActivity.class);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                startActivity(signup);
                break;
            case R.id.txt_edit_profile_nav_header:
                Intent edit = new Intent(DietPlan.this, EditProfile.class);
                edit.putExtra(LoginActivity.USER_TYPE,user_type);
                drawer_lay_diet.closeDrawer(nav_view_diet);
                startActivity(edit);
                break;
        }
    }
}
