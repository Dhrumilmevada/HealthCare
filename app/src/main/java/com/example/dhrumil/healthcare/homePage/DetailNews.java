package com.example.dhrumil.healthcare.homePage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.homePage.news.adapter.FeedAdapter;

public class DetailNews extends AppCompatActivity {

    private Toolbar tool_bar_detail_news;
    private WebView web_view_detail_news;
    private ProgressBar pro_bar_detail_news;
    private String url_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        inti();
        register();
        getIntentData();
        setWebView();
    }

    private void inti() {
        tool_bar_detail_news = (Toolbar) findViewById(R.id.tool_bar_detail_news);
        web_view_detail_news = (WebView) findViewById(R.id.web_view_detail_news);
        pro_bar_detail_news = findViewById(R.id.pro_bar_detail_news);
    }


    private void register() {
        tool_bar_detail_news.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(tool_bar_detail_news);

    }

    private void getIntentData(){
        Intent detail = getIntent();
        url_link = detail.getStringExtra(FeedAdapter.URL_LINK);
    }
    private void setWebView(){
        web_view_detail_news.getSettings().setBuiltInZoomControls(true);
        web_view_detail_news.getSettings().setDisplayZoomControls(false);
        web_view_detail_news.loadUrl(url_link);
        web_view_detail_news.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView webview , int progress){
                if(progress == 100)
                {
                    pro_bar_detail_news.setVisibility(View.GONE);
                    web_view_detail_news.setVisibility(View.VISIBLE);
                }
                else
                {
                    pro_bar_detail_news.setVisibility(View.VISIBLE);
                    web_view_detail_news.setVisibility(View.GONE);
                }
            }
        });
    }
}
