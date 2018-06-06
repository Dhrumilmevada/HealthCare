package com.example.dhrumil.healthcare.appointment.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;

/**
 * Created by Dhrumil on 5/10/2018.
 */

public class DoctorDetailAppoint extends Fragment {
    private View view;
    private TextView txt_pat_name;
    private TextView txt_pat_age;
    private TextView txt_pat_weight;
    private TextView txt_appointment;
    private TextView txt_appointment_date;
    private TextView txt_appointment_time;
    private TextView txt_desc_appoint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doctor_detail_appoint,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inti();
    }

    private void inti() {
        txt_pat_name = view.findViewById(R.id.txt_pat_name);
        txt_pat_age = view.findViewById(R.id.txt_pat_age);
        txt_pat_weight = view.findViewById(R.id.txt_pat_weight);
        txt_appointment = view.findViewById(R.id.txt_appointment);
        txt_appointment_time = view.findViewById(R.id.txt_appointment_time);
        txt_appointment_date = view.findViewById(R.id.txt_appointment_date);
        txt_desc_appoint = view.findViewById(R.id.txt_desc_appoint);
    }
}
