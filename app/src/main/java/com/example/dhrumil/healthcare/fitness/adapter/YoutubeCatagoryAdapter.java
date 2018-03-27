package com.example.dhrumil.healthcare.fitness.adapter;

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
import com.example.dhrumil.healthcare.fitness.model.YoutubeCatagoryModel;
import com.example.dhrumil.healthcare.homePage.news.interFace.ItemClickListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dhrumil on 3/26/2018.
 */
class YoutubeCatagoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_fitness_title;
    public CircleImageView cir_img_fitness;

    private ItemClickListener itemClickListener;

    public YoutubeCatagoryViewHolder(View itemView) {
        super(itemView);

        txt_fitness_title = (TextView) itemView.findViewById(R.id.txt_fitness_title);
        cir_img_fitness = (CircleImageView) itemView.findViewById(R.id.cir_img_fitness);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}

public class YoutubeCatagoryAdapter extends RecyclerView.Adapter<YoutubeCatagoryViewHolder> {

    private Context mContext;
    private ArrayList<YoutubeCatagoryModel> catagoryList;
    private LayoutInflater inflater;

    public YoutubeCatagoryAdapter(Context mContext, ArrayList<YoutubeCatagoryModel> catagoryList) {
        this.mContext = mContext;
        this.catagoryList = catagoryList;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public YoutubeCatagoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.youtube_video_raw_catagory_2,parent,false);
        return new YoutubeCatagoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YoutubeCatagoryViewHolder holder, int position) {
        final String fitness_title = catagoryList.get(position).getTitle();

        holder.txt_fitness_title.setText(fitness_title);

        holder.cir_img_fitness.setImageResource(catagoryList.get(position).getImage());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, Boolean isLongClick) {
                Intent i = new Intent(mContext, YoutubeThumbnailView.class);
                i.putExtra(Config.FITNESS_TITLE,fitness_title);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catagoryList.size();
    }
}
