package com.example.dhrumil.healthcare.hospital.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.homePage.news.interFace.ItemClickListener;
import com.example.dhrumil.healthcare.hospital.HospitalDetail;
import com.example.dhrumil.healthcare.hospital.model.HospitalListModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Dhrumil on 4/3/2018.
 */

public class HospitalListAdapter extends  RecyclerView.Adapter<HospitalListAdapter.HospitalListViewHolder>{

    private ArrayList<HospitalListModel> hospitalList;
    private Context mContext;
    private LayoutInflater inflater;
    private Drawable img;

    public HospitalListAdapter(ArrayList<HospitalListModel> hospitalList, Context mContext) {
        this.hospitalList = hospitalList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        img = ContextCompat.getDrawable(mContext,R.drawable.ic_star_yellow_24dp);
    }

    @Override
    public HospitalListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.hospital_raw_layout,parent,false);
        return new HospitalListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HospitalListViewHolder holder, int position) {
        int rating;
        holder.txt_hospital_name.setText(hospitalList.get(position).getHospitalName());
        holder.txt_hospital_distance.setText(hospitalList.get(position).getHospitalDistance());
        holder.txt_hospital_speciality.setText(hospitalList.get(position).getHospitalSpeciality());
        holder.txt_hospital_open_hour.setText(hospitalList.get(position).getHospitalTime());
        rating = hospitalList.get(position).getHospitalRating();

        if(rating == 1)
        {
            holder.img_view_rate_1.setImageDrawable(img);
        }
        else if (rating == 2){
            holder.img_view_rate_1.setImageDrawable(img);
            holder.img_view_rate_2.setImageDrawable(img);
        }
        else if (rating == 3){
            holder.img_view_rate_1.setImageDrawable(img);
            holder.img_view_rate_2.setImageDrawable(img);
            holder.img_view_rate_3.setImageDrawable(img);
        }
        else if (rating == 4){
            holder.img_view_rate_1.setImageDrawable(img);
            holder.img_view_rate_2.setImageDrawable(img);
            holder.img_view_rate_3.setImageDrawable(img);
            holder.img_view_rate_4.setImageDrawable(img);
        }
        else if(rating == 5){
            holder.img_view_rate_1.setImageDrawable(img);
            holder.img_view_rate_2.setImageDrawable(img);
            holder.img_view_rate_3.setImageDrawable(img);
            holder.img_view_rate_4.setImageDrawable(img);
            holder.img_view_rate_5.setImageDrawable(img);
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, Boolean isLongClick) {
                Intent i = new Intent(mContext,HospitalDetail.class);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    class HospitalListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txt_hospital_name;
        private TextView txt_hospital_distance;
        private TextView txt_hospital_speciality;
        private TextView txt_hospital_open_hour;
        private ImageView img_view_rate_1;
        private ImageView img_view_rate_2;
        private ImageView img_view_rate_3;
        private ImageView img_view_rate_4;
        private ImageView img_view_rate_5;

        private ItemClickListener itemClickListener;

        public HospitalListViewHolder(View itemView) {
            super(itemView);
            txt_hospital_name = (TextView) itemView.findViewById(R.id.txt_hospital_name);
            txt_hospital_distance = (TextView) itemView.findViewById(R.id.txt_hospital_distance);
            txt_hospital_speciality = (TextView) itemView.findViewById(R.id.txt_hospital_speciality);
            txt_hospital_open_hour = (TextView) itemView.findViewById(R.id.txt_hospital_open_hour);
            img_view_rate_1  = (ImageView) itemView.findViewById(R.id.img_view_rate_1);
            img_view_rate_2  = (ImageView) itemView.findViewById(R.id.img_view_rate_2);
            img_view_rate_3  = (ImageView) itemView.findViewById(R.id.img_view_rate_3);
            img_view_rate_4  = (ImageView) itemView.findViewById(R.id.img_view_rate_4);
            img_view_rate_5  = (ImageView) itemView.findViewById(R.id.img_view_rate_5);

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
}
