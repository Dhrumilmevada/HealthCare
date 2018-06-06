package com.example.dhrumil.healthcare.hospital;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.appointment.Appointment;
import com.example.dhrumil.healthcare.casePaper.CasePaper;
import com.example.dhrumil.healthcare.common.Config;
import com.example.dhrumil.healthcare.common.DistanceBetweenLocation;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.diet.DietPlan;
import com.example.dhrumil.healthcare.editProfile.EditProfile;
import com.example.dhrumil.healthcare.fitness.FitnessRoutine;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.homePage.news.common.NetworkStateReceiver;
import com.example.dhrumil.healthcare.hospital.adapter.HospitalListAdapter;
import com.example.dhrumil.healthcare.hospital.common.GpsStateReceiver;
import com.example.dhrumil.healthcare.hospital.model.HospitalListModel;
import com.example.dhrumil.healthcare.laboratory.LaboratoryList;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.yoga.YogaRoutine;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dhrumil.healthcare.R.id.nav_view_appointment;


public class HospitalList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,
                            NetworkStateReceiver.NetworkStateReceiverListener,GpsStateReceiver.GpsStateReceiverListener{

    private String user_type;
    private DrawerLayout drawer_lay_hospital_list;
    private Toolbar tool_bar_hospital;
    private NavigationView nav_view_hospital;
    private ActionBarDrawerToggle drawerToggle;
    private Context mContext;
    private SharedPreference preferences;
    private View rel_lay_nav_header;
    private CircleImageView circle_image_profile_nav_header;
    private TextView txt_name_nav_header;
    private TextView txt_email_nav_header;
    private TextView txt_edit_profile_nav_header;
    private RecyclerView rec_view_hospital_list;
    private ArrayList<HospitalListModel> hospitalList;
    private LinearLayoutManager linearLayoutManager;
    private HospitalListAdapter hospitalAdapter;
    private TextView txt_signup_nav_header;
    private TextView txt_login_nav_header;
    private DistanceBetweenLocation distance;
    private NetworkStateReceiver networkStateReceiver;
    private GpsStateReceiver gpsStateReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.REQUEST_LOCATION);
        inti();
        register();
        getIntentData();
        setNavigationDrawer();
        loadHospitalList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver,new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        gpsStateReceiver.addListener(this);
        registerReceiver(gpsStateReceiver,new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkStateReceiver.removeListener(this);
        unregisterReceiver(networkStateReceiver);

        gpsStateReceiver.removeListener(this);
        unregisterReceiver(gpsStateReceiver);
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

        rec_view_hospital_list = (RecyclerView) findViewById(R.id.rec_view_hospital_list);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        hospitalList = new ArrayList<HospitalListModel>();

        distance = new DistanceBetweenLocation(this);
        networkStateReceiver = new NetworkStateReceiver();
        gpsStateReceiver = new GpsStateReceiver();
    }

    private void register()
    {
        nav_view_hospital.setNavigationItemSelectedListener(this);
        rec_view_hospital_list.setLayoutManager(linearLayoutManager);
    }

    private void getIntentData(){
        Intent i = getIntent();
        user_type = i.getStringExtra(LoginActivity.USER_TYPE);
    }

    public void loadHospitalList(){
        hospitalList.clear();
        HospitalListModel hospital = new HospitalListModel();
        hospital.setHospitalName("L.D. College of Engineering");

        String location = "No.120, Circular Road,University Area, Opp Gujarat University,Navrangpura, Ahmedabad - 380015.GUJARAT INDIA";
        String dis = distance.getDistance(location);
        if(dis == null){
            hospital.setHospitalDistance("Error in loading...");
        }
        else {
            hospital.setHospitalDistance(dis+ "Km away");
        }
        hospital.setHospitalSpeciality("Cardiologist");
        hospital.setHospitalTime("9:00 to 19:00");
        hospital.setHospitalRating(3);
        hospitalList.add(hospital);
        hospitalAdapter = new HospitalListAdapter(hospitalList,this);
        rec_view_hospital_list.setAdapter(hospitalAdapter);
        hospitalAdapter.notifyDataSetChanged();
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


            rel_lay_nav_header =  nav_view_hospital.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);
        }
        else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_hospital.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_hospital.inflateHeaderView(R.layout.nav_header_view);

            menu = nav_view_hospital.getMenu();
            MenuItem menuItem = menu.getItem(2);
            menuItem.setChecked(true);


            rel_lay_nav_header =  nav_view_hospital.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);
        }
        else {
            nav_view_hospital.inflateMenu(R.menu.nav_menu_naive);
            nav_view_hospital.inflateHeaderView(R.layout.nav_header_naive);
            menu = nav_view_hospital.getMenu();
            MenuItem menuItem = menu.getItem(1);
            menuItem.setChecked(true);
            rel_lay_nav_header = nav_view_hospital.getHeaderView(0);
            txt_signup_nav_header = rel_lay_nav_header.findViewById(R.id.txt_signup_nav_header);
            txt_login_nav_header = rel_lay_nav_header.findViewById(R.id.txt_login_nav_header);
            txt_login_nav_header.setOnClickListener(this);
            txt_signup_nav_header.setOnClickListener(this);
        }

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
            case R.id.nav_book_appointment:
                item.setChecked(true);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                Intent book = new Intent(HospitalList.this,Appointment.class);
                book.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(book);
                break;
            case R.id.nav_case_paper:
                item.setChecked(true);
                drawer_lay_hospital_list.closeDrawer(nav_view_appointment);
                Intent paper = new Intent(HospitalList.this,CasePaper.class);
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
                Intent main = new Intent(HospitalList.this,MainActivity.class);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                startActivity(main);
                break;
            case R.id.txt_signup_nav_header:
                Intent signup = new Intent(HospitalList.this,LoginActivity.class);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                startActivity(signup);
                break;
            case R.id.txt_edit_profile_nav_header:
                Intent edit = new Intent(HospitalList.this, EditProfile.class);
                edit.putExtra(LoginActivity.USER_TYPE,user_type);
                drawer_lay_hospital_list.closeDrawer(nav_view_hospital);
                startActivity(edit);
                break;

        }
    }

    @Override
    public void networkAvailable() {
        loadHospitalList();
    }

    @Override
    public void networkUnavailable() {
        Toast.makeText(this,"No Intnernet Connection",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gpsAvailable() {
        loadHospitalList();
    }

    @Override
    public void gpsUnAvailable() {
        Toast.makeText(this,"Turn On GPS",Toast.LENGTH_LONG).show();
    }
}
