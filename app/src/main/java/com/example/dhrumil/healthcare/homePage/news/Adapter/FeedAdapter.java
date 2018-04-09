package com.example.dhrumil.healthcare.homePage.news.adapter;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.homePage.DetailNews;
import com.example.dhrumil.healthcare.homePage.news.common.ISO8601Parser;
import com.example.dhrumil.healthcare.homePage.news.interFace.ItemClickListener;
import com.example.dhrumil.healthcare.homePage.news.model.Articles;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dhrumil on 3/4/2018.
 */

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public CircleImageView cir_img_thumbnail;
    public TextView txt_news_title;
    public RelativeTimeTextView txt_publish_time;

   // public TextView txt_content;

    private ItemClickListener itemClickListener;


    public FeedViewHolder(View itemView) {
        super(itemView);

        txt_news_title = (TextView) itemView.findViewById(R.id.txt_news_title);
        txt_publish_time = (RelativeTimeTextView)itemView.findViewById(R.id.txt_publish_time);
        cir_img_thumbnail = (CircleImageView) itemView.findViewById(R.id.cir_img_thumbnail);

        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
    }

}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{

    private ArrayList<Articles> articles;
    private Context mContext;
    private LayoutInflater inflater;
    public final static String URL_LINK = "URL_LINK";

    public FeedAdapter(ArrayList<Articles> articles, Context mContext) {
        this.articles = articles;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_news,parent,false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, int position) {
        Picasso.with(mContext).load(articles.get(position).getUrlToImage()).into(holder.cir_img_thumbnail);

        if(articles.get(position).getTitle().length()>62)
            holder.txt_news_title.setText(articles.get(position).getTitle().substring(0,62)+"...");
        else
            holder.txt_news_title.setText(articles.get(position).getTitle());

        Date date = null;
        try {
            date = ISO8601Parser.parse(articles.get(position).getPublishedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txt_publish_time.setReferenceTime(date.getTime());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, Boolean isLongClick) {
                   if (!isLongClick ) {
                       /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articles.get(position).getUrl()));
                       browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       mContext.startActivity(browserIntent);*/
                       Intent detail = new Intent(mContext, DetailNews.class);
                       detail.putExtra(URL_LINK,articles.get(position).getUrl());
                       mContext.startActivity(detail);
                   }

            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

}
