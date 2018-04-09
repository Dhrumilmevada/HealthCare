package com.example.dhrumil.healthcare.homePage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.appointment.Appointment;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.diet.DietPlan;
import com.example.dhrumil.healthcare.editProfile.EditProfile;
import com.example.dhrumil.healthcare.fitness.FitnessRoutine;
import com.example.dhrumil.healthcare.homePage.news.adapter.FeedAdapter;
import com.example.dhrumil.healthcare.homePage.news.common.HttpDataHandler;
import com.example.dhrumil.healthcare.homePage.news.common.NetworkStateReceiver;
import com.example.dhrumil.healthcare.homePage.news.model.Articles;
import com.example.dhrumil.healthcare.hospital.HospitalList;
import com.example.dhrumil.healthcare.laboratory.LaboratoryList;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.yoga.YogaRoutine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;


import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class HomePage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,NavigationView.OnNavigationItemSelectedListener
                                                            ,NetworkStateReceiver.NetworkStateReceiverListener,View.OnClickListener{

    private String user_type;
    private DrawerLayout drawer_lay_home_page;
    private NavigationView nav_view_home_page;
    private Toolbar tool_bar_home;
    private ViewPager view_pager_image_home_page;
    private CircleIndicator circle_indicator_home_page;
    private SliderViewPagerAdapter adapter;
    private int image[] = {/*R.drawable.hospital, R.drawable.laboratory,*/R.drawable.diet,R.drawable.fitness,R.drawable.yoga};
    private View rel_lay_nav_header;
    private CircleImageView circle_image_profile_nav_header;
    private TextView txt_name_nav_header;
    private TextView txt_email_nav_header;
    private TextView txt_edit_profile_nav_header;
    private TextView txt_signup_nav_header;
    private TextView txt_login_nav_header;
    private ActionBarDrawerToggle drawerToggle;
    private static Context mContext;
    private SharedPreference preferences;

    //for news
    private RecyclerView rec_view_news_home;
    private String API_KEY = "7fe48093e70d461baba0312a3da7a283";
    private String get_data_url = "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=7fe48093e70d461baba0312a3da7a283";
    private LinearLayoutManager linearLayoutManager;
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";
    public static final String KEY_ARTICLES = "articles";
    private Articles articles;
    private ArrayList<Articles> data;
    private SwipeRefreshLayout swipe_lay_news;
    private DatabaseReference db;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "HomePage";
    private Uri uri;
    private String name;
    private String email;


    private NetworkStateReceiver networkStateReceiver;
    private ImageView img_view_no_internet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getIntentData();
        inti();
        register();
        setNavigationDrawer();
    }

    private void inti() {
        drawer_lay_home_page = (DrawerLayout) findViewById(R.id.drawer_lay_home_page);
        nav_view_home_page = (NavigationView) findViewById(R.id.nav_view_home_page);
        tool_bar_home = (Toolbar) findViewById(R.id.tool_bar_home);
        tool_bar_home.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(tool_bar_home);

        view_pager_image_home_page = (ViewPager) findViewById(R.id.view_pager_image_home_page);
        circle_indicator_home_page = (CircleIndicator) findViewById(R.id.circle_indicator_home_page);
        adapter = new SliderViewPagerAdapter(this, image,user_type);
        view_pager_image_home_page.setAdapter(adapter);
        circle_indicator_home_page.setViewPager(view_pager_image_home_page);

        drawerToggle = new ActionBarDrawerToggle(this, drawer_lay_home_page, R.string.open, R.string.close);
        drawer_lay_home_page.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();

        //news

        rec_view_news_home = (RecyclerView) findViewById(R.id.rec_view_news_home);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        data = new ArrayList<Articles>();
        swipe_lay_news = (SwipeRefreshLayout) findViewById(R.id.swipe_lay_news);
        img_view_no_internet = (ImageView) findViewById(R.id.img_view_no_internet);
        networkStateReceiver = new NetworkStateReceiver();

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference().child("login_string");
    }

    private void register() {
        rec_view_news_home.setLayoutManager(linearLayoutManager);
        nav_view_home_page.setNavigationItemSelectedListener(this);
        swipe_lay_news.setOnRefreshListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkStateReceiver.removeListener(this);
        unregisterReceiver(networkStateReceiver);
    }

    private void getIntentData() {
         Intent i = getIntent();
        user_type = i.getStringExtra(LoginActivity.USER_TYPE);
       // String uri2 = i.getStringExtra(LoginActivity.URI1);

        //uri = Uri.parse(i.getStringExtra(MainActivity.URI));
        name = i.getStringExtra(LoginActivity.NAME);
        email = i.getStringExtra(LoginActivity.EMAIL_ID);
    }


    private void loadNews() {
        AsyncTask<String, String, String> loadRssAsync = new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HttpDataHandler httpHandler = new HttpDataHandler();
                result = httpHandler.getHttpData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String result) {

                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONArray articlesArray = jsonResponse.getJSONArray(KEY_ARTICLES);
                    data.clear();
                    for (int i = 0; i < articlesArray.length(); i++) {
                        JSONObject objectArticle = articlesArray.getJSONObject(i);
                        articles = new Articles(objectArticle.optString(KEY_AUTHOR), objectArticle.optString(KEY_TITLE),
                                objectArticle.optString(KEY_DESCRIPTION), objectArticle.optString(KEY_URL),
                                objectArticle.optString(KEY_URLTOIMAGE), objectArticle.optString(KEY_PUBLISHEDAT));
                        data.add(articles);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                FeedAdapter adapter = new FeedAdapter(data, getBaseContext());
                rec_view_news_home.setAdapter(adapter);
                //rec_view_news_home.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
        };
        loadRssAsync.execute(get_data_url);
    }


    @Override
    public void onBackPressed() {
        if (drawer_lay_home_page.isDrawerOpen(nav_view_home_page)) {
            drawer_lay_home_page.closeDrawer(nav_view_home_page);
        }
       /* else if(!(user_type.equals(getResources().getString(R.string.patient))
                || user_type.equals(getResources().getString(R.string.doctor)))){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }*/
        else {
            super.onBackPressed();
        }
    }

    private void setProfileWidgets(){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " );

        final String uid = firebaseAuth.getCurrentUser().getUid().toString();
        DatabaseReference db1 = db.child(uid);
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " );
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("ANY_TAG", "entered onDataChange");
                Map<String,String> map= (Map) dataSnapshot.getValue();
                name = map.get("name");
                user_type = map.get("user_type");
                email = map.get("email");
             //   uri1 = Uri.parse(map.get("image"));
                Toast.makeText(HomePage.this, "invalid Username and password" , Toast.LENGTH_SHORT).show();

                /*user = dataSnapshot.getValue(User.class);
                  user_type = user.user_type;*/
               /* Map<String,String> map=dataSnapshot.getValue(Map.class);
                  String name = map.get("name");
                  user_type = map.get("user_type");*/
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(HomePage.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        });

        //txt_email_nav_header.setText(settings.getEmail());
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
        //setProfileWidgets();
        if (user_type.equals(getResources().getString(R.string.patient))) {
            nav_view_home_page.inflateMenu(R.menu.nav_menu_patient);
            nav_view_home_page.inflateHeaderView(R.layout.nav_header_view);

            rel_lay_nav_header =  nav_view_home_page.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            txt_name_nav_header.setText(name);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);
            /*try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                circle_image_profile_nav_header.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
            txt_email_nav_header.setText(email);
            txt_edit_profile_nav_header.setOnClickListener(this);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_home_page.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_home_page.inflateHeaderView(R.layout.nav_header_view);

            rel_lay_nav_header =  nav_view_home_page.getHeaderView(0);
            txt_name_nav_header = (TextView) rel_lay_nav_header.findViewById(R.id.txt_name_nav_header);
            txt_email_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_email_nav_header);
            txt_edit_profile_nav_header = (TextView)rel_lay_nav_header.findViewById(R.id.txt_edit_profile_nav_header);
            txt_name_nav_header.setText(name);
            txt_email_nav_header.setText(email);
            circle_image_profile_nav_header = (CircleImageView) rel_lay_nav_header.findViewById(R.id.circle_image_profile_nav_header);

            txt_edit_profile_nav_header.setOnClickListener(this);
        }
        else {
            nav_view_home_page.inflateMenu(R.menu.nav_menu_naive);
            nav_view_home_page.inflateHeaderView(R.layout.nav_header_naive);

            rel_lay_nav_header = nav_view_home_page.getHeaderView(0);
            txt_signup_nav_header = rel_lay_nav_header.findViewById(R.id.txt_signup_nav_header);
            txt_login_nav_header = rel_lay_nav_header.findViewById(R.id.txt_login_nav_header);
            txt_login_nav_header.setOnClickListener(this);
            txt_signup_nav_header.setOnClickListener(this);
        }

        Menu menu = nav_view_home_page.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
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
    public void onRefresh() {
        if(haveNetworkConnection()) {
            img_view_no_internet.setVisibility(View.GONE);
            rec_view_news_home.setVisibility(View.VISIBLE);
            loadNews();
        }
        else
        {
            img_view_no_internet.setVisibility(View.VISIBLE);
            rec_view_news_home.setVisibility(View.GONE);
            Toast.makeText(this,"Couldn't refresh feed",Toast.LENGTH_SHORT).show();

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipe_lay_news.setRefreshing(false);
            }
        }, 4000);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Menu menu = nav_view_home_page.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }


    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_news_feed:
                item.setChecked(true);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                Intent news = new Intent(HomePage.this,HomePage.class);
                news.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(news);
                finish();
                break;
            case R.id.nav_logout:
                item.setChecked(true);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                preferences = new SharedPreference(this,SharedPreference.USER_INFO);
                preferences.clear();
                Intent  logout = new Intent(HomePage.this,MainActivity.class);
                startActivity(logout);
                finish();
                break;
            case R.id.nav_hospital:
                item.setChecked(true);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                Intent hospital = new Intent(HomePage.this, HospitalList.class);
                hospital.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(hospital);
                break;
            case R.id.nav_laboratory:
                item.setChecked(true);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                Intent laboratory = new Intent(HomePage.this,LaboratoryList.class);
                laboratory.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(laboratory);
                break;
            case R.id.nav_diet:
                item.setChecked(true);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                Intent diet = new Intent(HomePage.this, DietPlan.class);
                diet.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(diet);
                break;
            case R.id.nav_workout:
                item.setChecked(true);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                Intent workout = new Intent(HomePage.this, FitnessRoutine.class);
                workout.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(workout);
                break;
            case R.id.nav_yago_meditation:
                item.setChecked(true);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                Intent yoga = new Intent(HomePage.this, YogaRoutine.class);
                yoga.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(yoga);
                break;
            case R.id.nav_book_appointment:
                item.setChecked(true);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                Intent book = new Intent(HomePage.this,Appointment.class);
                book.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(book);
                break;
        }
        return true;
    }

    @Override
    public void networkAvailable() {
        img_view_no_internet.setVisibility(View.GONE);
        rec_view_news_home.setVisibility(View.VISIBLE);
        loadNews();
    }

    @Override
    public void networkUnavailable() {
        img_view_no_internet.setVisibility(View.VISIBLE);
        rec_view_news_home.setVisibility(View.GONE);
        Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_login_nav_header:
                Intent main = new Intent(HomePage.this,MainActivity.class);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                startActivity(main);
                break;
            case R.id.txt_signup_nav_header:
                Intent signup = new Intent(HomePage.this,LoginActivity.class);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                startActivity(signup);
                break;
            case R.id.txt_edit_profile_nav_header:
                Intent edit = new Intent(HomePage.this, EditProfile.class);
                edit.putExtra(LoginActivity.USER_TYPE,user_type);
                drawer_lay_home_page.closeDrawer(nav_view_home_page);
                startActivity(edit);
                break;

        }
    }
}
