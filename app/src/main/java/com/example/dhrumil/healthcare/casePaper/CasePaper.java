package com.example.dhrumil.healthcare.casePaper;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.SlashScreen;
import com.example.dhrumil.healthcare.appointment.Appointment;
import com.example.dhrumil.healthcare.appointment.adapter.AppointmentAdapter;
import com.example.dhrumil.healthcare.appointment.model.AppointmentData;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.diet.DietPlan;
import com.example.dhrumil.healthcare.editProfile.EditProfile;
import com.example.dhrumil.healthcare.fitness.FitnessRoutine;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.hospital.HospitalList;
import com.example.dhrumil.healthcare.laboratory.LaboratoryList;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.yoga.YogaRoutine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dhrumil.healthcare.R.id.float_btn_appointment;
import static com.example.dhrumil.healthcare.R.id.float_btn_case_paper;
import static com.example.dhrumil.healthcare.R.id.nav_view_appointment;
import static com.example.dhrumil.healthcare.R.id.tool_bar_case_paper;

public class CasePaper extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private String user_type;
    private DrawerLayout drawer_lay_case_paper;
    private Toolbar tool_bar_case_paper;
    private NavigationView nav_view_case_paper;
    private ActionBarDrawerToggle drawerToggle;
    private Context mContext;
    private View rel_lay_nav_header;
    private CircleImageView circle_image_profile_nav_header;
    private TextView txt_name_nav_header;
    private TextView txt_email_nav_header;
    private TextView txt_edit_profile_nav_header;
    private SharedPreference preferences;
    private RecyclerView rec_view_case_paper;
    private FloatingActionButton float_btn_case_paper;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<AppointmentData> appointmentsList;
    private AppointmentData appointment;
    private Map<String,String> map;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;
    private String name,email,image,usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_paper);
        getIntentData();
        inti();
        register();
        setNavigationDrawer();
    }


    private void inti() {
        drawer_lay_case_paper = (DrawerLayout) findViewById(R.id.drawer_lay_case_paper);
        tool_bar_case_paper = (Toolbar) findViewById(R.id.tool_bar_case_paper);
        nav_view_case_paper = (NavigationView)findViewById(R.id.nav_view_case_paper);
        tool_bar_case_paper.setTitle(getResources().getString(R.string.case_paper_1));
        setSupportActionBar(tool_bar_case_paper);

        drawerToggle = new ActionBarDrawerToggle(this,drawer_lay_case_paper,R.string.open,R.string.close);
        drawer_lay_case_paper.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();

        rec_view_case_paper = findViewById(R.id.rec_view_case_paper);
        float_btn_case_paper = findViewById(R.id.float_btn_case_paper);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        appointmentsList = new ArrayList<AppointmentData>();
        rec_view_case_paper.setLayoutManager(linearLayoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference().child("casepaper");
    }

    private void register() {
        nav_view_case_paper.setNavigationItemSelectedListener(this);
        float_btn_case_paper.setOnClickListener(this);
      //  getdata();
        appointment = new AppointmentData("Nayan","3/2/4590","3:2");
        appointmentsList.add(appointment);
        AppointmentAdapter adapter = new AppointmentAdapter(appointmentsList,this,user_type);
        rec_view_case_paper.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

   /* private void getdata() {
        final String user_id = firebaseAuth.getCurrentUser().getUid().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String displayName = user.getDisplayName();
        DatabaseReference db1;
        Log.d("ANY_TAG", "entered onDataChange");
        if (displayName.equals(db.child(displayName))) {
            db1 = db.child(displayName);
        } else {
            db1 = db.child(user_id);
        }
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ANY_TAG", "entered onDataChange");
                map = (Map) dataSnapshot.getValue();
                name = map.get("name");
                usertype = map.get("user_type");
                email = map.get("email");
                image = map.get("image");
                Intent accountsIntent = new Intent(SlashScreen.this, HomePage.class);
                accountsIntent.putExtra(MainActivity.USER_TYPE, usertype);
                accountsIntent.putExtra(MainActivity.NAME, name);
                accountsIntent.putExtra(MainActivity.EMAIL_ID, email);
                accountsIntent.putExtra(MainActivity.URI, image);
                startActivity(accountsIntent);
                finish();
                //}else{
                //  Toast.makeText(MainActivity.this, "invalid Username and password" , Toast.LENGTH_SHORT).show();
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SlashScreen.this, "invalid Username and password", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

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
            nav_view_case_paper.inflateMenu(R.menu.nav_menu_patient);
            nav_view_case_paper.inflateHeaderView(R.layout.nav_header_view);

            menu = nav_view_case_paper.getMenu();
            MenuItem menuItem = menu.getItem(4);
            menuItem.setChecked(true);


            rel_lay_nav_header =  nav_view_case_paper.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_case_paper.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_case_paper.inflateHeaderView(R.layout.nav_header_view);

            menu = nav_view_case_paper.getMenu();
            MenuItem menuItem = menu.getItem(5);
            menuItem.setChecked(true);


            rel_lay_nav_header =  nav_view_case_paper.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            txt_edit_profile_nav_header.setOnClickListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer_lay_case_paper.isDrawerOpen(nav_view_appointment))
        {
            drawer_lay_case_paper.closeDrawer(nav_view_appointment);
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
            menu = nav_view_case_paper.getMenu();
            MenuItem menuItem = menu.getItem(4);
            menuItem.setChecked(true);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            menu = nav_view_case_paper.getMenu();
            MenuItem menuItem = menu.getItem(5);
            menuItem.setChecked(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_news_feed:
                item.setChecked(true);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                Intent news = new Intent(CasePaper.this,HomePage.class);
                news.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(news);
                break;
            case R.id.nav_book_appointment:
                item.setChecked(true);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                Intent book = new Intent(CasePaper.this,Appointment.class);
                book.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(book);
                break;
            case R.id.nav_case_paper:
                item.setChecked(true);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                Intent paper = new Intent(CasePaper.this,CasePaper.class);
                paper.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(paper);
                finish();
                break;
            case R.id.nav_logout:
                item.setChecked(true);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                preferences = new SharedPreference(this,SharedPreference.USER_INFO);
                preferences.clear();
                Intent  logout = new Intent(CasePaper.this,MainActivity.class);
                startActivity(logout);
                finish();
                break;
            case R.id.nav_hospital:
                item.setChecked(true);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                Intent hospital = new Intent(CasePaper.this, HospitalList.class);
                hospital.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(hospital);
                break;
            case R.id.nav_laboratory:
                item.setChecked(true);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                Intent laboratory = new Intent(CasePaper.this, LaboratoryList.class);
                laboratory.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(laboratory);
                break;
            case R.id.nav_diet:
                item.setChecked(true);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                Intent diet = new Intent(CasePaper.this, DietPlan.class);
                diet.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(diet);
                break;
            case R.id.nav_workout:
                item.setChecked(true);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                Intent workout = new Intent(CasePaper.this, FitnessRoutine.class);
                workout.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(workout);
                break;
            case R.id.nav_yago_meditation:
                item.setChecked(true);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                Intent yoga = new Intent(CasePaper.this, YogaRoutine.class);
                yoga.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(yoga);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_edit_profile_nav_header:
                Intent edit = new Intent(CasePaper.this, EditProfile.class);
                edit.putExtra(LoginActivity.USER_TYPE,user_type);
                drawer_lay_case_paper.closeDrawer(nav_view_appointment);
                startActivity(edit);
                break;
            case R.id.float_btn_case_paper:
                Intent paper= new Intent(CasePaper.this,NewCasePaper.class);
                startActivity(paper);
                break;
        }
    }

  
}
