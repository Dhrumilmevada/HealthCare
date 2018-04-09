package com.example.dhrumil.healthcare.fitness;

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
import com.example.dhrumil.healthcare.common.YoutubeData;
import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.diet.DietPlan;
import com.example.dhrumil.healthcare.editProfile.EditProfile;
import com.example.dhrumil.healthcare.fitness.adapter.YoutubeCatagoryAdapter;
import com.example.dhrumil.healthcare.fitness.model.YoutubeCatagoryModel;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.hospital.HospitalList;
import com.example.dhrumil.healthcare.laboratory.LaboratoryList;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.yoga.YogaRoutine;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FitnessRoutine extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private String user_type;
    private DrawerLayout drawer_lay_fitness;
    private Toolbar tool_bar_fitness;
    private RecyclerView rec_view_fitness;
    private LinearLayoutManager linearLayoutManager;
    private NavigationView nav_view_fitness;
    private ActionBarDrawerToggle drawerToggle;
    private Context mContext;
    private SharedPreference preferences;
    private YoutubeData youtubeData;
    private ArrayList<YoutubeCatagoryModel> catagoryList;
    private YoutubeCatagoryAdapter youtubeCatagoryAdapter;
    private View rel_lay_nav_header;
    private CircleImageView circle_image_profile_nav_header;
    private TextView txt_name_nav_header;
    private TextView txt_email_nav_header;
    private TextView txt_edit_profile_nav_header;

    private TextView txt_signup_nav_header;
    private TextView txt_login_nav_header;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_routine);
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
        drawer_lay_fitness = (DrawerLayout) findViewById(R.id.drawer_lay_fitness);
        tool_bar_fitness = (Toolbar) findViewById(R.id.tool_bar_fitness);
        nav_view_fitness = (NavigationView) findViewById(R.id.nav_view_fitness);
        tool_bar_fitness.setTitle(getResources().getString(R.string.work_out));
        setSupportActionBar(tool_bar_fitness);

        drawerToggle = new ActionBarDrawerToggle(this,drawer_lay_fitness,R.string.open,R.string.close);
        drawer_lay_fitness.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();

        rec_view_fitness = (RecyclerView) findViewById(R.id.rec_view_fitness);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        youtubeData = new YoutubeData();
        catagoryList = new ArrayList<YoutubeCatagoryModel>();
        getFilledCatagoryList();
        youtubeCatagoryAdapter = new YoutubeCatagoryAdapter(this,catagoryList);
        db = new Database(this);
    }

    private void register() {
        nav_view_fitness.setNavigationItemSelectedListener(this);
        rec_view_fitness.setLayoutManager(linearLayoutManager);
        rec_view_fitness.setAdapter(youtubeCatagoryAdapter);
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
            nav_view_fitness.inflateMenu(R.menu.nav_menu_patient);
            nav_view_fitness.inflateHeaderView(R.layout.nav_header_view);

            menu = nav_view_fitness.getMenu();
            MenuItem menuItem = menu.getItem(6);
            menuItem.setChecked(true);

            rel_lay_nav_header =  nav_view_fitness.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_fitness.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_fitness.inflateHeaderView(R.layout.nav_header_view);

            menu = nav_view_fitness.getMenu();
            MenuItem menuItem = menu.getItem(7);
            menuItem.setChecked(true);

            rel_lay_nav_header =  nav_view_fitness.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);
        }
        else {
            nav_view_fitness.inflateMenu(R.menu.nav_menu_naive);
            nav_view_fitness.inflateHeaderView(R.layout.nav_header_naive);
            menu = nav_view_fitness.getMenu();
            MenuItem menuItem = menu.getItem(4);
            menuItem.setChecked(true);
            rel_lay_nav_header = nav_view_fitness.getHeaderView(0);
            txt_signup_nav_header = rel_lay_nav_header.findViewById(R.id.txt_signup_nav_header);
            txt_login_nav_header = rel_lay_nav_header.findViewById(R.id.txt_login_nav_header);
            txt_login_nav_header.setOnClickListener(this);
            txt_signup_nav_header.setOnClickListener(this);
        }


    }

    @Override
    public void onBackPressed() {
        if(drawer_lay_fitness.isDrawerOpen(nav_view_fitness))
        {
            drawer_lay_fitness.closeDrawer(nav_view_fitness);
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
            menu = nav_view_fitness.getMenu();
            MenuItem menuItem = menu.getItem(6);
            menuItem.setChecked(true);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            menu = nav_view_fitness.getMenu();
            MenuItem menuItem = menu.getItem(8);
            menuItem.setChecked(true);
        } else {
            menu = nav_view_fitness.getMenu();
            MenuItem menuItem = menu.getItem(4);
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
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                Intent news = new Intent(FitnessRoutine.this,HomePage.class);
                news.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(news);
                break;
            case R.id.nav_logout:
                item.setChecked(true);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                preferences = new SharedPreference(this,SharedPreference.USER_INFO);
                preferences.clear();
                Intent  logout = new Intent(FitnessRoutine.this,MainActivity.class);
                startActivity(logout);
                finish();
                break;
            case R.id.nav_hospital:
                item.setChecked(true);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                Intent hospital = new Intent(FitnessRoutine.this, HospitalList.class);
                hospital.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(hospital);
                break;
            case R.id.nav_laboratory:
                item.setChecked(true);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                Intent laboratory = new Intent(FitnessRoutine.this, LaboratoryList.class);
                laboratory.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(laboratory);
                break;
            case R.id.nav_diet:
                item.setChecked(true);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                Intent diet = new Intent(FitnessRoutine.this, DietPlan.class);
                diet.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(diet);
                break;
            case R.id.nav_workout:
                item.setChecked(true);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                Intent workout = new Intent(FitnessRoutine.this, FitnessRoutine.class);
                workout.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(workout);
                finish();
                break;
            case R.id.nav_yago_meditation:
                item.setChecked(true);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                Intent yoga = new Intent(FitnessRoutine.this, YogaRoutine.class);
                yoga.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(yoga);
                break;
            case R.id.nav_book_appointment:
                item.setChecked(true);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                Intent book = new Intent(FitnessRoutine.this,Appointment.class);
                book.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(book);
                break;
        }
        return true;
    }

    private void getFilledCatagoryList() {
        String[] title = youtubeData.fitness_title;
        int img [] = youtubeData.fitness_image;

        for (int i= 0 ; i< title.length ; i++)
        {
            YoutubeCatagoryModel youtubeCatagoryModel = new YoutubeCatagoryModel(img[i],title[i]);
            catagoryList.add(youtubeCatagoryModel);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_login_nav_header:
                Intent main = new Intent(FitnessRoutine.this,MainActivity.class);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                startActivity(main);
                break;
            case R.id.txt_signup_nav_header:
                Intent signup = new Intent(FitnessRoutine.this,LoginActivity.class);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                startActivity(signup);
                break;
            case R.id.txt_edit_profile_nav_header:
                Intent edit = new Intent(FitnessRoutine.this, EditProfile.class);
                edit.putExtra(LoginActivity.USER_TYPE,user_type);
                drawer_lay_fitness.closeDrawer(nav_view_fitness);
                startActivity(edit);
                break;
        }
    }
}
