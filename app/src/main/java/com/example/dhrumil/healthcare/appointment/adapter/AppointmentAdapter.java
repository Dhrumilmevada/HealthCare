package com.example.dhrumil.healthcare.appointment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.appointment.Appointment;
import com.example.dhrumil.healthcare.appointment.DetailAppointment;
import com.example.dhrumil.healthcare.appointment.model.AppointmentData;
import com.example.dhrumil.healthcare.casePaper.CasePaper;
import com.example.dhrumil.healthcare.casePaper.DetailCasePaper;
import com.example.dhrumil.healthcare.homePage.news.interFace.ItemClickListener;
import com.example.dhrumil.healthcare.login.LoginActivity;

import java.util.ArrayList;

/**
 * Created by Dhrumil on 5/9/2018.
 */

class AppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_appointment_name;
    public TextView txt_appointment_date;
    public TextView txt_appointment_time;
    private ItemClickListener itemClickListener;

    public AppointmentViewHolder(View itemView) {
        super(itemView);
        txt_appointment_name = itemView.findViewById(R.id.txt_appointment_name);
        txt_appointment_date = itemView.findViewById(R.id.txt_appointment_date);
        txt_appointment_time = itemView.findViewById(R.id.txt_appointment_time);

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
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentViewHolder>{

    private ArrayList<AppointmentData> appointments;
    private Context mContext;
    private LayoutInflater inflater;
    private String user_type;

    public AppointmentAdapter(ArrayList<AppointmentData> appointments, Context mContext,String user_type) {
        this.appointments = appointments;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.user_type = user_type;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.appointment_row,parent,false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {

        holder.txt_appointment_name.setText(appointments.get(position).getName());
        holder.txt_appointment_date.setText(appointments.get(position).getDate());
        holder.txt_appointment_time.setText(appointments.get(position).getTime());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, Boolean isLongClick) {
                if(mContext instanceof Appointment) {
                    Intent detail = new Intent(mContext, DetailAppointment.class);
                    detail.putExtra(LoginActivity.USER_TYPE, user_type);
                    mContext.startActivity(detail);
                }
                else if(mContext instanceof CasePaper){
                    Intent detail = new Intent(mContext,DetailCasePaper.class);
                    detail.putExtra(LoginActivity.USER_TYPE, user_type);
                    mContext.startActivity(detail);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
