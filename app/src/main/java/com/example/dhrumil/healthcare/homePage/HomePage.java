package com.example.dhrumil.healthcare.homePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.homePage.news.adapter.FeedAdapter;
import com.example.dhrumil.healthcare.homePage.news.common.HttpDataHandler;
import com.example.dhrumil.healthcare.homePage.news.model.Articles;
import com.example.dhrumil.healthcare.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import me.relex.circleindicator.CircleIndicator;

public class HomePage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,NavigationView.OnNavigationItemSelectedListener {

    private String user_type;
    private DrawerLayout drawer_lay_home_page;
    private NavigationView nav_view_home_page;
    private Toolbar tool_bar_home;
    private ViewPager view_pager_image_home_page;
    private CircleIndicator circle_indicator_home_page;
    private SliderViewPagerAdapter adapter;
    private int image[] = {R.drawable.hospital, R.drawable.laboratory,R.drawable.diet,R.drawable.fitness,R.drawable.yoga};
    private View rel_lay_nav_header;
    private ActionBarDrawerToggle drawerToggle;
    private static Context mContext;
    private SharedPreferences preferences;

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
    public final static String ON_BACK_PRESS = "ON_BACK_PRESS";
    //private Handler handler;
    //private Timer swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        inti();
        register();
        getIntentData();
        setNavigationDrawer();

        // setSlidePageListner();
        //setTimerSlider();

    }

    private void inti() {
        drawer_lay_home_page = (DrawerLayout) findViewById(R.id.drawer_lay_home_page);
        nav_view_home_page = (NavigationView) findViewById(R.id.nav_view_home_page);
        tool_bar_home = (Toolbar) findViewById(R.id.tool_bar_home);
        tool_bar_home.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(tool_bar_home);

        view_pager_image_home_page = (ViewPager) findViewById(R.id.view_pager_image_home_page);
        circle_indicator_home_page = (CircleIndicator) findViewById(R.id.circle_indicator_home_page);
        adapter = new SliderViewPagerAdapter(this, image);
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
        // handler = new Handler();
        //swipe = new Timer();
    }

    private void register() {
        rec_view_news_home.setLayoutManager(linearLayoutManager);
        nav_view_home_page.setNavigationItemSelectedListener(this);
        swipe_lay_news.setOnRefreshListener(this);
        if (haveNetworkConnection())
            loadNews();
        else
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
    }


    private void getIntentData() {
        Intent i = getIntent();
        user_type = i.getStringExtra(LoginActivity.USER_TYPE);
       /* if (user_type == null) {
            user_type = i.getStringExtra(Splash_screen.user_data);
        }*/
    }


    private void loadNews() {
        AsyncTask<String, String, String> loadRssAsync = new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {

                rec_view_news_home.setVisibility(View.GONE);
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
                //rssObject = new Gson().fromJson(s,RssObject.class);
                FeedAdapter adapter = new FeedAdapter(data, getBaseContext());
                rec_view_news_home.setAdapter(adapter);
                rec_view_news_home.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
        };

        //StringBuilder url_get_data = new StringBuilder(rss_to_json_api);
        //url_get_data.append(rss_link);
        loadRssAsync.execute(get_data_url);
    }


    @Override
    public void onBackPressed() {
        if (drawer_lay_home_page.isDrawerOpen(nav_view_home_page)) {
            drawer_lay_home_page.closeDrawer(nav_view_home_page);
        }
        else if(!(user_type.equals(getResources().getString(R.string.patient))
                || user_type.equals(getResources().getString(R.string.doctor)))){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        /*if (item.getItemId() == R.id.menu_refresh) {
            if (haveNetworkConnection())
                loadNews();
            else
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();

    }


    private void setNavigationDrawer() {
        if (user_type.equals(getResources().getString(R.string.patient))) {
            nav_view_home_page.inflateMenu(R.menu.nav_menu_patient);
            nav_view_home_page.inflateHeaderView(R.layout.nav_header_view);

        } else if (user_type.equals(getResources().getString(R.string.doctor))) {
            nav_view_home_page.inflateMenu(R.menu.nav_menu_doctor);
            nav_view_home_page.inflateHeaderView(R.layout.nav_header_view);
        } else {
            nav_view_home_page.inflateMenu(R.menu.nav_menu_naive);
            nav_view_home_page.inflateHeaderView(R.layout.nav_header_naive);
        }

        //rel_lay_nav_header =  nav_view_home_page.getHeaderView(0);
        Menu menu = nav_view_home_page.getMenu();
        MenuItem menuItem = menu.getItem(0).setChecked(true);

    }

    public static boolean haveNetworkConnection() {
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
        loadNews();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipe_lay_news.setRefreshing(false);
            }
        },4000);
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                preferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent  i = new Intent(HomePage.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        return true;
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
