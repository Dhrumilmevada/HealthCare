package com.example.dhrumil.healthcare.diet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.common.Config;
import com.example.dhrumil.healthcare.common.YoutubeThumbnailView;
import com.example.dhrumil.healthcare.diet.model.YoutubeCatagoryModel;
import com.example.dhrumil.healthcare.homePage.news.interFace.ItemClickListener;
import com.example.dhrumil.healthcare.login.LoginActivity;

import java.util.ArrayList;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dhrumil on 3/24/2018.
 */

class YoutubeCatagoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_catagory_title;
    public TextView txt_catagory_desc;
    public CircleImageView cir_img_diet;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public YoutubeCatagoryViewHolder(View itemView) {
        super(itemView);

        txt_catagory_title = itemView.findViewById(R.id.txt_catagory_title);
        txt_catagory_desc = itemView.findViewById(R.id.txt_catagory_desc);
        cir_img_diet = itemView.findViewById(R.id.cir_img_diet);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
public class YoutubeCatagoryAdapter extends RecyclerView.Adapter<YoutubeCatagoryViewHolder>{

    private Context mContext;
    private ArrayList<YoutubeCatagoryModel> catagoryList;
    private LayoutInflater inflater;

    public YoutubeCatagoryAdapter(Context mContext, ArrayList<YoutubeCatagoryModel> catagoryList) {
        this.mContext = mContext;
        this.catagoryList = catagoryList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public YoutubeCatagoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.youtube_video_raw_catagory,parent,false);
        return new YoutubeCatagoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YoutubeCatagoryViewHolder holder, int position) {

        final String diet_title = catagoryList.get(position).getTitle();

        holder.cir_img_diet.setImageResource(catagoryList.get(position).getImage());
        holder.txt_catagory_title.setText(diet_title);

        holder.txt_catagory_desc.setText(catagoryList.get(position).getDesc());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, Boolean isLongClick) {
                Intent i = new Intent(mContext, YoutubeThumbnailView.class);
                i.putExtra(Config.DIET_TITLE,diet_title);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catagoryList.size();
    }
}
